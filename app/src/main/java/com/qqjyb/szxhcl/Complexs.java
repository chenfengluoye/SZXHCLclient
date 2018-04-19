package com.qqjyb.szxhcl;

/**
 * Created by chenkaiju on 2017/10/22.
 */

public class Complexs {
    private final double r;
    private final double i;

    public Complexs (double r, double i) {
        this.r = r;
        this.i = i;
    }

    public double abs() { // return sqrt(r^2 +i^2)
        return Math.hypot(r, i);
    }

    public double phase() {
        return Math.atan2(i, r);
    }

    public Complexs plus (Complexs c) {
        return new Complexs (this.r + c.r, this.i + c.i);
    }

    public Complexs minus (Complexs c) {
        return new Complexs (this.r - c.r, this.i - c.i);
    }

    public Complexs times (Complexs c) {
        return new Complexs (this.r * c.r - this.i * c.i,
                this.r * c.i + this.i * c.r);
    }

    public Complexs times (double d) {
        return new Complexs (this.r * d, this.i * d);
    }

    public Complexs conjugate() {
        return new Complexs (r, -i);
    }

    public double getR () {
        return r;
    }

    public double getI () {
        return i;
    }

    public double getMod(){
        return Math.sqrt(this.r*this.r+this.i*this.i);
    }
    public Complexs exp() {
        return new Complexs(Math.exp(r) * Math.cos(i),
                Math.exp(r) * Math.sin(i));
    }

    public static Complexs[] fft (Complexs[] x) {
        int N = x.length;
        if (N == 1) {
            return x;
        }

        // radix 2 Cooley-Turkey FFT
        if (N % 2 != 0) {
            throw new RuntimeException("N is not a power of 2");
        }

        // fft of even terms
        Complexs[] even = new Complexs[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complexs[] q = fft(even);

        // fft of odd terms
        Complexs[] odd = new Complexs[N/2];
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k+1];
        }
        Complexs[] r = fft(odd);

        // combine
        Complexs[] y = new Complexs[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complexs wk = new Complexs(Math.cos(kth), Math.sin(kth)); // all small number not 0
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }

        return y;
    }
}
