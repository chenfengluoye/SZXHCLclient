package com.qqjyb.szxhcl;

import java.util.Objects;

/**
 * Created by chenkaiju on 2017/10/22.
 */

public class OtherComplex {
//    private final double re;   // the real part
//    private final double im;   // the imaginary part
//
//    // create a new object with the given real and imaginary parts
//    public OtherComplex(double real, double imag) {
//        re = real;
//        im = imag;
//    }
//
//    // return a string representation of the invoking Complex object
//    public String toString() {
//        if (im == 0) return re + "";
//        if (re == 0) return im + "i";
//        if (im <  0) return re + " - " + (-im) + "i";
//        return re + " + " + im + "i";
//    }
//
//    // return abs/modulus/magnitude
//    public double abs() {
//        return Math.hypot(re, im);
//    }
//
//    // return angle/phase/argument, normalized to be between -pi and pi
//    public double phase() {
//        return Math.atan2(im, re);
//    }
//
//    // return a new Complex object whose value is (this + b)
//    public OtherComplex plus(OtherComplex b) {
//        OtherComplex a = this;             // invoking object
//        double real = a.re + b.re;
//        double imag = a.im + b.im;
//        return new OtherComplex(real, imag);
//    }
//
//    // return a new Complex object whose value is (this - b)
//    public OtherComplex minus(OtherComplex b) {
//        OtherComplex a = this;
//        double real = a.re - b.re;
//        double imag = a.im - b.im;
//        return new OtherComplex(real, imag);
//    }
//
//    // return a new Complex object whose value is (this * b)
//    public OtherComplex times(OtherComplex b) {
//        OtherComplex a = this;
//        double real = a.re * b.re - a.im * b.im;
//        double imag = a.re * b.im + a.im * b.re;
//        return new OtherComplex(real, imag);
//    }
//
//    // return a new object whose value is (this * alpha)
//    public OtherComplex scale(double alpha) {
//        return new OtherComplex(alpha * re, alpha * im);
//    }
//
//    // return a new Complex object whose value is the conjugate of this
//    public OtherComplex conjugate() {
//        return new OtherComplex(re, -im);
//    }
//
//    // return a new Complex object whose value is the reciprocal of this
//    public OtherComplex reciprocal() {
//        double scale = re*re + im*im;
//        return new OtherComplex(re / scale, -im / scale);
//    }
//
//    // return the real or imaginary part
//    public double re() { return re; }
//    public double im() { return im; }
//
//    // return a / b
//    public OtherComplex divides(OtherComplex b) {
//        OtherComplex a = this;
//        return a.times(b.reciprocal());
//    }
//
//    // return a new Complex object whose value is the complex exponential of this
//    public OtherComplex exp() {
//        return new OtherComplex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
//    }
//
//    // return a new Complex object whose value is the complex sine of this
//    public OtherComplex sin() {
//        return new OtherComplex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
//    }
//
//    // return a new Complex object whose value is the complex cosine of this
//    public OtherComplex cos() {
//        return new OtherComplex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
//    }
//
//    // return a new Complex object whose value is the complex tangent of this
//    public OtherComplex tan() {
//        return sin().divides(cos());
//    }
//
//
//
//    // a static version of plus
//    public static OtherComplex plus(OtherComplex a, OtherComplex b) {
//        double real = a.re + b.re;
//        double imag = a.im + b.im;
//        OtherComplex sum = new OtherComplex(real, imag);
//        return sum;
//    }
//
//    // See Section 3.3.
//    public boolean equals(Object x) {
//        if (x == null) return false;
//        if (this.getClass() != x.getClass()) return false;
//        OtherComplex that = (OtherComplex) x;
//        return (this.re == that.re) && (this.im == that.im);
//    }
//
//    // See Section 3.3.
//    public int hashCode() {
//        return Objects.hash(re, im);
//    }
//
//    public Double getMod() {
//        return Math.sqrt(this.re*this.re+this.im*this.im);
//    }
}
