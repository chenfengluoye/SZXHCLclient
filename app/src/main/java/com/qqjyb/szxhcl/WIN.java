package com.qqjyb.szxhcl;

/**
 * Created by chenkaiju on 2017/11/6.
 */

public class WIN {

    //各种窗函数
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


}
