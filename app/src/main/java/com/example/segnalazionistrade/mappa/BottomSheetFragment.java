package com.example.segnalazionistrade.mappa;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.segnalazionistrade.R;
import com.example.segnalazionistrade.segnalazioni.IncidenteActivity;
import com.example.segnalazionistrade.segnalazioni.StradaChiusaActivity;
import com.example.segnalazionistrade.segnalazioni.SosActivity;
import com.example.segnalazionistrade.segnalazioni.TrafficoActivity;
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
                SendToIncidenteActivity();
            }
        });

        stradaChiusa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToStradaCiusaActivity();
            }
        });


        traffico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToTrafficoActivity();

            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToSosActivity();
            }
        });


        return v;

    }

    private void SendToSosActivity() {
        Intent i = new Intent(getContext(), SosActivity.class);
        startActivity(i);
    }

    private void SendToTrafficoActivity() {
        Intent i = new Intent(getContext(), TrafficoActivity.class);
        startActivity(i);
    }

    private void SendToStradaCiusaActivity() {
        Intent i = new Intent(getContext(), StradaChiusaActivity.class);
        startActivity(i);
    }

    private void SendToIncidenteActivity() {
        Intent i = new Intent(getContext(), IncidenteActivity.class);
        startActivity(i);
    }

    public BottomSheetFragment() {
    }
}
