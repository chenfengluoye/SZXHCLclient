package com.qqjyb.szxhcl;

/**
 * Created by chenkaiju on 2017/10/28.
 */

public class FIR {

    //阻带衰减,根据该值确定窗函数的类型wn
    static  double As=40;

    //未知参数
    static  double Rp=5;

    //通带边缘频率
    static double Wp=6;

    //阻带起始频率
    static  double Ws=4;

    //截止频率
    static  double Wc=6;

    //通带波纹
    static  double Qp=6;

    //阻带波纹,该值不能大于1
    static  double Qs=0.1;


    //滤波器阶数
    static int n=30;

    //滤波器类型,1低通，2高通，3带通，4带阻
    static int band=1;

    //通带允许的频率衰减
    static double fln=0.3;

    //阻带允许的频率频率衰减
    static double fhn=0.6;

    //窗函数的类型，1矩形窗，2图基窗，3三角窗，4汉宁窗，5,海明窗，6布拉克曼窗,7凯撒窗
    static int wn=1;

    static double beta=6;
    //采样频率
    static double fs=415;

    static double pi=Math.PI;

    static float [] firwin(int n,int band,double fln,double fhn,int wn,double beta) {
        int i,n2,mid;
        double s,pi,wc1,wc2=0,delay;
        float h[]=new float[n+1];
        pi=Math.PI;
        if((n%2)==0)
        {
            n2=n/2-1;
            mid=1;
        }
        else
        {
            n2=n/2;
            mid=0;
        }
        delay=n/2.0;
        wc1=2.0*pi*fln;
        if(band>=3)
            wc2=2.0*pi*fhn;
        switch(band)
        {
            case 1://低通滤波器
            {
                for(i=0;i<=n2;i++) {
                    s=i-delay;
                    h[i]= (float) ((Math.sin(wc1*s)/(pi*s))*window(wn,n+1,i,beta));
                    h[n-i]=h[i];
                }
                if(mid==1)
                    h[n/2]= (float) (wc1/pi);
                break;
            }
            case 2://高通滤波器
            {
                for(i=0;i<=n2;i++)
                {
                    s=i-delay;
                    Double temp=(Math.sin(pi*s)-Math.sin(wc1*s))/(pi*s);
                    h[i]= temp.floatValue();
                    temp=temp*window(wn,n+1,i,beta);
                    h[i]= temp.floatValue();
                    h[n-i]=h[i];
                }
                if(mid==1)
                    h[n/2]= (float) (1.0-wc1/pi);
                break;
            }
            case 3://带通滤波器
            {
                for(i=0;i<=n2;i++)
                {
                    s=i-delay;
                    h[i]= (float) ((Math.sin(wc2*s)-Math.sin(wc1*s))/(pi*s));
                    h[i]= (float) (h[i]*window(wn,n+1,i,beta));
                    h[n-i]=h[i];
                }
                if(mid==1)
                    h[n/2]= (float) ((wc2-wc1)/pi);
                break;
            }
            case 4://带阻滤波器
            {
                for(i=0;i<=n2;i++)
                {
                    s=i-delay;
                    Double temp=(Math.sin(wc1*s)+Math.sin(pi*s)-Math.sin(wc2*s))/(pi*s);
                    h[i]= temp.floatValue();
                    temp=temp*window(wn,n+1,i,beta);
                    h[i]= temp.floatValue();
                    h[n-i]=h[i];
                }
                if(mid==1)
                    h[n/2]= (float) ((wc1+pi-wc2)/pi);
                break;
            }
        }
        return h;
    }
    static double window(int type,int n,int i,double beta) {
        int k;
        double pi,w;
        pi=4.0*Math.atan(1.0);
        w=1.0;
        switch(type)
        {
            case 1:
            {
                w=1.0;
                break;
            }
            case 2:
            {
                k=(n-2)/10;
                if(i<=k)
                    w=0.5*(1.0-Math.cos(i*pi/(k+1)));
                if(i>n-k-2)
                    w=0.5*(1.0-Math.cos((n-i-1)*pi/(k+1)));
                break;
            }
            case 3:
            {
                w=1.0-Math.abs(1.0-2*i/(n-1.0));
                break;
            }
            case 4:
            {
                w=0.5*(1.0-Math.cos(2*i*pi/(n-1)));
                break;
            }

            case 5:
            {
                w=0.54-0.46*Math.cos(2*i*pi/(n-1));
                break;
            }
            case 6:
            {
                w=0.42-0.5*Math.cos(2*i*pi/(n-1))+0.08*Math.cos(4*i*pi/(n-1));
                break;
            }
            case 7:
            {
                w=kasier(i,n,beta);
                break;
            }
        }
        return w;
    }

    static float min(float a, float b)
    {
        return a < b ? a : b;
    }

    //计算卷积
    static float[] convolution(float []input1, float []input2)
    {
        int mm=input1.length;
        int nn=input2.length;
        float []output=new float[mm + nn - 1];
        float []xx = new float[mm + nn - 1];
        float []tempinput2 = new float[mm + nn - 1];
        for (int i = 0; i < nn; i++)
        {
            tempinput2[i] = input2[i];
        }
        for (int i = nn; i < mm + nn - 1; i++)
        {
            tempinput2[i] = (float) 0.0;
        }

        for (int i = 0; i < mm + nn - 1; i++)
        {
            xx[i] = (float) 0.0;
            int tem = (int) ((min(i, mm)) == mm ? mm-1 : min(i, mm));
            for (int j = 0; j <= tem; j++)
            {
                xx[i] += (input1[j] * tempinput2[i - j]);
            }
        }
        for (int i = 0; i < mm+nn-1; i++)
            output[i] = xx[i];
        return xx;
    }

    static double kasier(int i,int n,double beta) {
        double a,w,a2,b1,b2,bata1;
        b1=bessel0(beta);
        a=2.0*i/(double)(n-1)-1.0;
        a2=a*a;
        bata1=beta*Math.sqrt(1.0-a2);
        b2=bessel0(bata1);
        w=b2/b1;
        return w;
    }
    static double bessel0(double x) {
        double d,y,d2,sum;
        y=x/2.0;
        d=1.0;
        sum=1.0;
        for(int i=1;i<=25;i++)
        {
            d=d*y/i;
            d2=d*d;
            sum=sum+d2;
            if(d2<sum*(1.0e-8))
                break;
        }
        return sum;
    }


    //设计低通滤波器
    //根据阻带衰减和过渡带带宽确定窗函数类型并获取滤波器阶数
    static void getWinTypeAndN(){
        fs=FirData.samplerate;
        Rp=-20*Math.log10((1-Qs)/(1+Qp));
        As=-20*Math.log10(Qs/(1+Qp));
        Wp=2*pi*fln/fs;
        Ws=2*pi*fhn/fs;
        double DtaW=Ws-Wp;
        if(As<=44){//矩形窗
            wn=1;
            n=new Double(1.8*Math.PI/DtaW).intValue()+1;
        }else if(As<74){//汉宁窗
            wn=4;
            n=new Double(6.2*Math.PI/DtaW).intValue()+1;
        }else if(As>74){//布拉克曼窗
            wn=6;
            n=new Double(11*Math.PI/DtaW).intValue()+1;
        }
        if(Qs>=0){
            Rp=0;
        }
        //获取采样频率
    }

    static float [] getHh(){
        getWinTypeAndN();
        float []temp=new float[n+1];
        int n2=0;
        int mid=0;
        double delay=n/2.0;
        if(n%2==0){
            n2=n/2-1;
        }else {
            n2=n/2;
            mid=1;
        }
        Wc=(Wp+Ws)/2;
        for(int i=0;i<n2;i++){
            double s=i-delay;
            temp[i]= (float) ((Wc/Math.PI)*Sinc(Wc*s/Math.PI)*window(wn,n+1,i,beta));
            temp[n-i]=temp[i];
        }
        if(mid==1){
            temp[n/2]= (float) (Wp/pi);
        }
        return temp;
    }

    static double Sinc(double x){
        double t=0;
        t=Math.sin(Math.PI*x)/(Math.PI*x);
        return t;
    }

}
