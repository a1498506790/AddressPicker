package com.github.addresspicker;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @data 2018-09-28
 * @desc
 */

public class AddressDialogFragment extends DialogFragment{

    private NumberPicker mNPProvince;
    private NumberPicker mNPCity;
    private NumberPicker mNPArea;

    private AddressModel mAddressModel;
    private int mIndex = 0;

    public static AddressDialogFragment newInstance() {
        Bundle args = new Bundle();
        AddressDialogFragment fragment = new AddressDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_address, container);
        mNPProvince = view.findViewById(R.id.numberProvince);
        mNPCity = view.findViewById(R.id.numberCity);
        mNPArea = view.findViewById(R.id.numberArea);

        mNPProvince.setWrapSelectorWheel(false);
        mNPCity.setWrapSelectorWheel(false);
        mNPArea.setWrapSelectorWheel(false);

        mNPProvince.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mNPCity.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mNPArea.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer sb = new StringBuffer();
                AddressModel.CityListModel cityListModel = mAddressModel.getCitylist().get(mNPProvince.getValue());
                sb.append(cityListModel.getName());
                AddressModel.CityListModel.CityModel cityModel = cityListModel.getCity().get(mNPCity.getValue());
                sb.append(cityModel.getName());
                sb.append(cityModel.getArea().get(mNPArea.getValue()));
                Toast.makeText(getActivity(), "选择了 : " + sb.toString(), Toast.LENGTH_SHORT).show();
                dismiss();

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initJsonDate();
    }

    private void initJsonDate() {
        AssetManager assets = getActivity().getAssets();
        try {
            InputStream is = assets.open("city.json");
            byte[] buf = new byte[is.available()];
            is.read(buf);
            String json = new String(buf, "UTF-8");
            Gson gson = new Gson();
            mAddressModel = gson.fromJson(json, AddressModel.class);
            setData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        final List<AddressModel.CityListModel> citylist = mAddressModel.getCitylist();
        String[] sP = new String[citylist.size()];
        for (int i = 0; i < citylist.size(); i++) {
            sP[i] = citylist.get(i).getName();
        }
        mNPProvince.setDisplayedValues(sP);
        mNPProvince.setMaxValue(sP.length - 1);

        AddressModel.CityListModel cityListModel = mAddressModel.getCitylist().get(0);
        String[] cP = new String[cityListModel.getCity().size()];
        for (int i = 0; i < cityListModel.getCity().size(); i++) {
            cP[i] = cityListModel.getCity().get(i).getName();
        }
        mNPCity.setDisplayedValues(cP);
        mNPCity.setMaxValue(cP.length - 1);

        AddressModel.CityListModel.CityModel cityModel = cityListModel.getCity().get(0);
        String[] aP = new String[cityModel.getArea().size()];
        for (int i = 0; i < cityModel.getArea().size(); i++) {
            aP[i] = cityModel.getArea().get(i);
        }
        mNPArea.setDisplayedValues(aP);
        mNPArea.setMaxValue(aP.length - 1);

        mNPProvince.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                mIndex = i1;
                AddressModel.CityListModel cityListModel = mAddressModel.getCitylist().get(i1);
                String[] sC = new String[cityListModel.getCity().size()];
                for (int i2 = 0; i2 < cityListModel.getCity().size(); i2++) {
                    sC[i2] = cityListModel.getCity().get(i2).getName();
                }
                mNPCity.setDisplayedValues(null);
                mNPCity.setMaxValue(sC.length - 1);
                mNPCity.setDisplayedValues(sC);
                mNPCity.setValue(0);

                AddressModel.CityListModel.CityModel cityModel = cityListModel.getCity().get(0);
                String[] aP = new String[cityModel.getArea().size()];
                for (int i3 = 0; i3 < cityModel.getArea().size(); i3++) {
                    aP[i3] = cityModel.getArea().get(i3);
                }
                mNPArea.setDisplayedValues(null);
                mNPArea.setMaxValue(aP.length - 1);
                mNPArea.setDisplayedValues(aP);
                mNPArea.setValue(0);
            }
        });

        mNPCity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                AddressModel.CityListModel.CityModel cityModel = mAddressModel.getCitylist().get(mIndex).getCity().get(i1);
                String[] aP = new String[cityModel.getArea().size()];
                for (int i3 = 0; i3 < cityModel.getArea().size(); i3++) {
                    aP[i3] = cityModel.getArea().get(i3);
                }
                mNPArea.setDisplayedValues(null);
                mNPArea.setMaxValue(aP.length - 1);
                mNPArea.setDisplayedValues(aP);
                mNPArea.setValue(0);
            }
        });


    }

    public void show(FragmentManager fragmentManager){
        super.show(fragmentManager, System.currentTimeMillis() + "");
    }
}
