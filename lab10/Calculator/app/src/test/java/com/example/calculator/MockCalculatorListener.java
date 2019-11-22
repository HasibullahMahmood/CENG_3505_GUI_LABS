package com.example.calculator;

class MockCalculatorListener implements CalculatorListener {

    int result;

    @Override
    public void onResultCalculated(int result) {
        this.result = result;
    }
}

