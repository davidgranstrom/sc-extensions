+ SequenceableCollection {
    // find the mode of a data set
    findMode {
        var sortedN = this.asSet.asArray.sort;
        var sorted  = this.copy.sort;
        var mode, maxItems, idx = [], counts = [];
        sortedN.do {|n|
            counts = counts.add(sorted.occurrencesOf(n));
        };
        // see if we have more than one mode
        maxItems = counts.select {|x| x == counts.maxItem };
        counts.do {|x,i|
            if(x == counts.maxItem) {
                idx = idx.add(i);
            }
        };
        if(idx.size <= 1) {
            mode = sortedN[idx[0]];
        } {
            mode = sortedN[idx];
        };
        ^mode;
    }
}
