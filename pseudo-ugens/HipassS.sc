// high pass filters
// connected in series

HipassS {
    
    *ar{|in, freq, num=2|       

        var sig= in/(num-1).max(1);
        
        num.do{
            sig= HPF.ar(sig, freq)
        };
        
        ^sig * num/2;
    }
}
