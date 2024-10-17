package com.example.carebridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DiagnosisTab extends Fragment {


    private CardView mcardview;
    private CardView meventCardView, mhomeCardView, mlifestyleCardView, msportCardView, mtechCardView, mtutorCardView;
    private NestedScrollView mnestedscroll;
    private ImageView meventimg, mhomeimg, mlifestyleimg, msportimg, mtechimg, mtutorimg;
    private TextView meventviewtxt, mhomeviewtxt, mlifeviewtxt, msportviewtxt, mtechviewtxt, mtutorviewtxt;

    private static int imgArray[]= {
            R.drawable.fever,R.drawable.dengue,R.drawable.headache,R.drawable.tuberculosis,R.drawable.influenza,R.drawable.diabetes
    };
    String[] diseaseName;
    String [] diseaseSymptom;
    String [] diseaseDescription;
    private static final String TAG = "AssessmentTab1";

    Activity context;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_diagnosis, container, false);

        context = getActivity();

        diseaseName = getResources().getStringArray(R.array.disease_names);
        diseaseSymptom = getResources().getStringArray(R.array.disease_symptoms);
        diseaseDescription = getResources().getStringArray(R.array.disease_treatment);

        mcardview = (CardView) rootView.findViewById(R.id.therapist);

        meventimg = (ImageView) rootView.findViewById(R.id.eventimg);
        mhomeimg = (ImageView) rootView.findViewById(R.id.homeimg);
        mlifestyleimg = (ImageView) rootView.findViewById(R.id.lifestyleimg);
        msportimg = (ImageView) rootView.findViewById(R.id.sportimg);
        mtechimg = (ImageView) rootView.findViewById(R.id.techimg);
        mtutorimg = (ImageView) rootView.findViewById(R.id.tutorimg);

        meventCardView = (CardView) rootView.findViewById(R.id.eventCardView);
        mhomeCardView = (CardView) rootView.findViewById(R.id.homeCardView);
        mlifestyleCardView = (CardView) rootView.findViewById(R.id.lifestyleCardView);
        msportCardView = (CardView) rootView.findViewById(R.id.sportCardView);
        mtechCardView = (CardView) rootView.findViewById(R.id.techCardView);
        mtutorCardView = (CardView) rootView.findViewById(R.id.tutorCardView);

        meventviewtxt = (TextView) rootView.findViewById(R.id.eventviewtxt);
        mhomeviewtxt = (TextView) rootView.findViewById(R.id.homeviewtxt);
        mlifeviewtxt = (TextView) rootView.findViewById(R.id.lifeviewtxt);
        msportviewtxt = (TextView) rootView.findViewById(R.id.sportviewtxt);
        mtechviewtxt = (TextView) rootView.findViewById(R.id.techviewtxt);
        mtutorviewtxt = (TextView) rootView.findViewById(R.id.tutorviewtxt);

        mcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(context, TheTherapist.class));
            }
        });

        loadCardView();
        return rootView;
    }

    private void loadCardView() {
        ImageView[] imageViews = { meventimg, mhomeimg, mlifestyleimg, msportimg, mtechimg, mtutorimg };
        for (int i= 0; i < imageViews.length; i++) {
            imageViews[i].setImageResource(imgArray[i]);
        }

        TextView[] textViews ={meventviewtxt, mhomeviewtxt, mlifeviewtxt, msportviewtxt, mtechviewtxt, mtutorviewtxt};
        for (int i=0; i < textViews.length; i++) {
            textViews[i].setText(diseaseName[i]);
        }

        CardView[] cardviews = { meventCardView, mhomeCardView, mlifestyleCardView, msportCardView, mtechCardView, mtutorCardView };
        for(int i=0; i < cardviews.length; i++) {
            CardView card = cardviews[i];
            final int pos = i;
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, DescriptionActivity.class);
                    intent.putExtra("destitle",diseaseName[pos]);
                    intent.putExtra("dessymp",diseaseSymptom[pos]);
                    intent.putExtra("destext",diseaseDescription[pos]);
                    intent.putExtra("desimage",imgArray[pos]);
                    startActivity(intent);

                    //startActivity(new Intent(context, DiseaseReport.class));
                }
            });
        }
    }

}

