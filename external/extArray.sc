+ Array {
    // Mid-Side processing
    //
    // Encoding
    //
    // M = (L + R) -3dB
    // S = (L - R) -3dB
    //
    // Decoding
    //
    // L = (M + S) -3dB
    // R = (M - S) -3dB
    msMatrix {
        var ch1, ch2;

        if(this.numChannels !== 2) {
            ^Error("Signal needs to be 2 channels").throw;
        };

        ch1 = (this[0] + this[1]) * -3.dbamp;
        ch2 = (this[0] - this[1]) * -3.dbamp;

        ^[ ch1, ch2 ];
    }

    toMS {
        ^this.msMatrix;
    }

    toLR {
        ^this.msMatrix;
    }
}
