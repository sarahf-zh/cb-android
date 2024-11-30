// See https://github.com/dialogflow/dialogflow-fulfillment-nodejs
// for Dialogflow fulfillment library docs, samples, and to report issues

'use esversion: 8';
const actions = require("actions-on-google");
// require('dotenv').config();
const axios = require('axios');
const functions = require('firebase-functions');
const {WebhookClient} = require('dialogflow-fulfillment');
 
process.env.DEBUG = 'dialogflow:debug';

exports.dialogflowFirebaseFulfillment = functions.https.onRequest((req, res) => {
    const agent = new WebhookClient({ request: req, response: res });
    console.log('Dialogflow Request headers: ' + JSON.stringify(req.headers));
    console.log('Dialogflow Request body: ' + JSON.stringify(req.body));

    let intentMap = new Map();
    intentMap.set("Default Welcome Intent", welcome);
    intentMap.set("Default Fallback Intent", general);
    intentMap.set("Diagnose Symptoms Intent", diagnosis);
    agent.handleRequest(intentMap);

    function welcome(agent) {
        console.log('Dialogflow - welcome');
        agent.add('Hello there! How can I help?');
    }

    async function general(agent) {
        console.log('Dialogflow - general');

        const prompt_general = 'The following is a conversation between a user and an AI agent, which is you.' +
            'You goal is helping a user with his or her general inquiries. ' +
            'Your response should be concise, complete and helpful.';

        return queryGPT(agent, prompt_general);
    }

    async function diagnosis(agent) {
        console.log('Dialogflow - diagnosis');

        const prompt_diagnosis = 'The following is a conversation between a user and a doctor, which is you.' +
            'You collect symptoms from a user ' +
            'then give a preliminary diagnosis of the most likely disease based on the given symptoms.' +
            'Along with the disease, you also suggest with one treatment such as what specific medicine to take.' +
            'Please make sure your response is formated as:' +
            'Based on your symptoms, it is very likely that you are having this disease: ' +
            'One treatment option to consider is taking this medicine:';

        return queryGPT(agent, prompt_diagnosis);
    }

    async function queryGPT(agent, prompt) {
        console.log('Dialogflow - queryGPT');

        const OPENAI_API_KEY = 'your api key';
        const ORGANIZATION_ID = 'your organization id';
        const MODEL_NAME= 'gpt-4o-mini';
        const instance = axios.create({
            baseURL: 'https://api.openai.com/v1/',
            headers: { Authorization: `Bearer ` + OPENAI_API_KEY, Organization: ORGANIZATION_ID},
            //headers: { Authorization: `Bearer ${process.env.OPENAI_API_KEY}` },
        });

        let query = agent.query;
        console.log('query text ', query);
        const dialog = [];
        dialog.push(prompt);
        dialog.push(`User: ${query}`);
        dialog.push('AI:');

        // model: MODEL_NAME,
        const completionParams = {
            prompt: dialog.join('\n'),
            max_tokens: 65,
            temperature: 0.25,
            n: 1,
            stream: false,
            logprobs: null,
            echo: false,
            stop: '\n',
        };

        try {
            const result = await instance.post('/engines/davinci-002/completions', completionParams);
            //const result = await instance.post('/chat/completions', completionParams);
            const botResponse = result.data.choices[0].text.trim();
            console.log('qpt response: ' + botResponse);
            agent.add(botResponse);
        } catch (err) {
            console.log("Got open AI error.");
            // Log specific error details
            if (err.response) {
                console.error('Response status:', err.response.status);
                console.error('Response data:', err.response.data);
            } else {
                console.error('Error:', err.message);
            }
            agent.add('Sorry. Something went wrong. Can you say that again?');
        }
    }

});
