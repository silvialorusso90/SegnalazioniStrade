package com.example.segnalazionistrade.mappa;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.segnalazionistrade.R;
import com.example.segnalazionistrade.segnalazioni.IncidenteActivity;
import com.example.segnalazionistrade.segnalazioni.StradaCiusaActivity;
import com.example.segnalazionistrade.segnalazioni.sosActivity;
import com.example.segnalazionistrade.segnalazioni.trafficoActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        ImageView incidente = v.findViewById(R.id.imgIncidente);
        ImageView stradaChiusa = v.findViewById(R.id.imgStradaChiusa);
        ImageView traffico = v.findViewById(R.id.imgTraffico);
        ImageView sos = v.findViewById(R.id.imgSos);

        incidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), IncidenteActivity.class);
                startActivity(i);

            }
        });

        stradaChiusa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), StradaCiusaActivity.class);
                startActivity(i);
            }
        });

        traffico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), trafficoActivity.class);
                startActivity(i);
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), sosActivity.class);
                startActivity(i);
            }
        });

        return v;

    }

    public BottomSheetFragment() {
    }
}
