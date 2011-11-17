// fill an array with random values and append leading and trailing zeroes.
// useful when creating level segments for envelopes.
// david granstrom, 22/09/2011

+ SimpleNumber {

    levels{|min, max, dist=\lin|
    
        var levels;

        levels= case
                    { dist==\lin }{ {rrand(min, max)}!(this-2) }
                    { dist==\exp }{ {rrand(min, max)**2}!(this-2) }
                    ;

        levels= [0] ++ levels ++ [0];
        ^levels;
    }
}
