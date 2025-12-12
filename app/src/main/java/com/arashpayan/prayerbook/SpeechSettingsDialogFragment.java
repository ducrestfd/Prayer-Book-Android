package com.arashpayan.prayerbook;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.slider.

        Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.text.DecimalFormat;

public class SpeechSettingsDialogFragment extends DialogFragment {

    public static final String TAG = "SpeechSettingsDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_speech_settings, null);

        final Prefs prefs = Prefs.get();
        final DecimalFormat formatter = new DecimalFormat("0.0x");

        // Rate Slider
        final Slider rateSlider = view.findViewById(R.id.rate_slider);
        final TextView rateValueText = view.findViewById(R.id.rate_value_text);
        rateSlider.setValue(prefs.getSpeechRate());
        rateValueText.setText(formatter.format(rateSlider.getValue()));
        rateSlider.addOnChangeListener((slider, value, fromUser) -> {
            rateValueText.setText(formatter.format(value));
        });

        // Pitch Slider
        final Slider pitchSlider = view.findViewById(R.id.pitch_slider);
        final TextView pitchValueText = view.findViewById(R.id.pitch_value_text);
        pitchSlider.setValue(prefs.getSpeechPitch());
        pitchValueText.setText(formatter.format(pitchSlider.getValue()));
        pitchSlider.addOnChangeListener((slider, value, fromUser) -> {
            pitchValueText.setText(formatter.format(value));
        });

        // Speak on open switch
        final SwitchMaterial speakOnOpenSwitch = view.findViewById(R.id.speak_on_open_switch);
        speakOnOpenSwitch.setChecked(prefs.getSpeakPrayerOnOpen());


        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.speech_settings)
                .setView(view)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    // Save the final values when the user clicks OK
                    prefs.setSpeechRate(rateSlider.getValue());
                    prefs.setSpeechPitch(pitchSlider.getValue());
                    prefs.setSpeakPrayerOnOpen(speakOnOpenSwitch.isChecked());
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

    }
}
