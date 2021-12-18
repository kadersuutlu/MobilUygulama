package com.example.market;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private static List<String> basketList = new ArrayList<>();
    private static MutableLiveData<List<String>> liveBasketList = new MutableLiveData<>();

    public static List<String> getBasketList() {
        return basketList;
    }

    public static void setBasketList(List<String> basketList) {
        Basket.basketList = basketList;
    }

    public static void addBasketList(String item) {
        Basket.basketList.add(item);
    }

    public static MutableLiveData<List<String>> getLiveBasketList() {
        return liveBasketList;
    }

    public static void setLiveBasketList(MutableLiveData<List<String>> liveBasketList) {
        Basket.liveBasketList = liveBasketList;
        setBasketList(liveBasketList.getValue());
    }

    public static void addLiveBasketList(String item) {
        if(Basket.liveBasketList.getValue() != null){
            Basket.liveBasketList.getValue().add(item);
            addBasketList(item);
        }
    }

    public static void addLiveBasketListWithNotify(String item) {//WithNotify
        addBasketList(item);
        Basket.liveBasketList.setValue(new ArrayList<>(getBasketList()));
    }
}
