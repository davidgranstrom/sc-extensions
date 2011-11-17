// low pass filters
// connected in series

LopassS {
    
    *ar{|in, freq, num=2|       

        var sig= in/(num-1).max(1);
        
        num.do{
            sig= LPF.ar(sig, freq)
        };
        
        ^sig * num/2;
    }
}
