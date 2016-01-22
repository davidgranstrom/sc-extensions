+ Array {
    // Mid-Side processing
    //
    // Encoding
    //
    // M = (L + R) -3dB
    // S = (L - R) -3dB
    toMS {
        var mid, side;

        if(this.size != 2) {
            "Signal needs to be 2 channels".throw;
        };

        mid  = (this[0] + this[1]) * -3.dbamp;
        side = (this[0] - this[1]) * -3.dbamp;

        ^[ mid, side ];
    }

    // Decoding
    //
    // L = (M + S) -3dB
    // R = (M - S) -3dB
    toLR {
        var left, right;

        if(this.size != 2) {
            "Signal needs to be 2 channels".throw;
        };

        left  = (this[0] + this[1]) * -3.dbamp;
        right = (this[0] - this[1]) * -3.dbamp;

        ^[ left, right ];
    }
}
