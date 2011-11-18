// tool for realizing stockhausen's piece "plus minus"
// david granstrom 31/10/2011 

PlusMinus {

    var <>layer; 
    var <>dlimit=0, <>ulimit=127; 
    var <>style=\default;

    var currentlayer;
    var midilist;

    *new{|... args|
        ^super.newCopyArgs(*args).initPlusMinus;
    }

    initPlusMinus{

        currentlayer= ();
        midilist= List[];
        ulimit= ulimit+1;

        case
        { layer.isNil } {
            "you must insert a layer argument".error;
            ^this.halt;
        }
        { dlimit>=ulimit } {
            "please increase your bounds".error;
            ^this.halt;
        }
        ;

        layer= layer.collect{|chord| chord.flat };
        currentlayer.putPairs(layer);
    }

    performLowest{|transp, input, output, key| 

        var prevchord, nextchord;
        var chord, diff; 
        var i= 0;

        chord= List.newUsing(input.asList);
        diff= input.differentiate.drop(1);

        //increment the chord if necessary
        if((transp+1)>chord.size, {
            while { chord.size<=transp }
            { 
                chord.add(diff[i%diff.size]+chord.last); 
                i= i+1; 
            }
        });

        // check if it fits the bounds..
        prevchord= chord.collect{|note|
            while { note<dlimit or:{note>ulimit} }
            { 
                note= note+dlimit%ulimit;
            };
        note;
        };

        // subtract fundamental pitch
        output= output-output.first;
        // replace w/ previous chords pitch
        nextchord= prevchord[transp]+output;

        // check if it fits the bounds..
        nextchord= nextchord.collect{|note|
            while { note<dlimit or:{note>ulimit} }
            { 
                note= note+dlimit%ulimit;
            };
        note;
        };

        // update the dictionary
        currentlayer.put(key, nextchord);
        midilist.add(nextchord);
        
        ^switch(style)
        {\default} { nextchord }
        {\note}    { nextchord.asNote }
        ;
    }

    performHighest{|transp, input, output, key|

        var prevchord, nextchord;
        var chord, diff; 
        var i= 0;

        chord= List.newUsing(input.reverse.asList);
        diff= chord.differentiate.drop(1);

        if((transp+1)>chord.size, {
            while { chord.size<=transp }
            { 
                chord.add(diff[i%(diff.size)]+chord.last); 
                i= i+1;
            }
        });

        // check if it fits the bounds..
        prevchord= chord.collect{|note|
            while { note<dlimit or:{note>ulimit} }
            { 
                note= note+dlimit%ulimit;
            };
        note;
        };

        // subtract the highest pitch
        output= output-output.last;
        // replace w/ previous chords highest pitch
        nextchord= prevchord[transp]+output;

        // check if it fits the bounds..
        nextchord= nextchord.collect{|note|
            while { note<dlimit or:{note>ulimit} }
            { 
                note= note+dlimit%ulimit;
            };
        note;
        };
    
        // update the dictionary
        currentlayer.put(key, nextchord);
        midilist.add(nextchord);
        
        ^switch(style)
        {\default} { nextchord }
        {\note}    { nextchord.asNote }
        ;
    }
    
    // this just returns the output chord
    returnChord{|output|
        var chord;
        // check if it fits the bounds..
        chord= currentlayer[output].collect{|note|
            while { note<dlimit or:{note>ulimit} }
            { 
                note= note+dlimit%ulimit;
            };
        note;
        };

        currentlayer.put(output, chord);
        midilist.add(chord);
        ^chord;
    }

    lowestBut{|transp, input, output|
        ^if(input.notNil, {
            this.performLowest(
                            transp, 
                            currentlayer[input],
                            currentlayer[output],
                            output
                            );
        }, {
            this.returnChord(output)
        });
    }

    highestBut{|transp, input, output|
        ^if(input.notNil, {
            this.performHighest(
                            transp, 
                            currentlayer[input],
                            currentlayer[output],
                            output
                            );
        }, {
            this.returnChord(output)
        });
    }

    play{|event, oct=0|
        ^if(event.notNil, {
            (midinote: currentlayer[event]+(oct*12), dur:2).play;
            currentlayer[event];
        }, { 
            "Supply an event first".error 
        });
    }

    writeMIDI{|path="~/Desktop/plusminus.mid", removeDups=false|

        var result= List[];
        var hashes= List[];
        var chords, pattern, render;
        var i= 1;

        "Writing MIDI file..".inform;

        if(removeDups.not, {

            midilist.do{|chord| result.add(chord) }

        }, {

            midilist.do({|chord|
               var hash= chord.hash;
               if(hashes.includes(hash).not, {
                       hashes.add(hash);
                       result.add(chord);
               });
            });
        });

        result.do{|chord, i|
            format("[ %/% ]: chord%", (i+1), result.size, chord).postln;
        };

        chords= Pseq(result, inf);
        pattern= Pbind(\midinote, chords, \dur, 2);
        render= SimpleMIDIFile(path.standardizePath);
        render.fromPattern(pattern, maxEvents: result.size);
        render.write;
        "Done!".postln;
    }

    clean{
        ^currentlayer.putPairs(layer);
    }

    dump{
        ^currentlayer;
    }
}
