// fill an array with random values with a fixed total sum.
// useful when creating time segments for envelopes.
// david granstrom, 22/09/2011

+ SimpleNumber {

    durations{|totalDur, dist=\lin|
    
        var durations, dur;
        var segment= totalDur/this;

        durations= case
                       { dist==\lin }{ {rrand(0, segment)}!this }
                       { dist==\exp }{ {exprand(1e-3, segment)}!this }
                       ;

        dur= durations/durations.sum;
        ^dur= dur.collect{|i| i*totalDur };
    }
}
