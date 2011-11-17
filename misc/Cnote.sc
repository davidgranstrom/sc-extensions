// chromatic steps, equal tempered scale
// adapted from the SuperCollider book, ch.17

+ SimpleNumber {
    cnote {|root=440, stepsPerOctave=12|
            ^2.pow(this/stepsPerOctave)*root
    }
}

+ SequenceableCollection {
    cnote {|root=440, stepsPerOctave=12|
            ^2.pow(this/stepsPerOctave)*root
    }
}

+ AbstractFunction {
    cnote {|root=440, stepsPerOctave=12|
            ^2.pow(this/stepsPerOctave)*root
    }
}
