// a dictionary for note names of different kind
// David Granstrom, 14/10/2011 

NoteDict {

    classvar <>instances;
    classvar small, big, numbered;

    var <>style= \default;
    var <>output;
    var <notes;

    *initClass{
        instances= ();
    }

    // use create instead
    *new{|... args|
        ^super.new.init.makeDict(*args); 
    }

    *create{|style=\default|
        if(instances[style].isNil, {
            instances.put(style, NoteDict(style));
        });
        ^instances[style]
    }

    *numberToNote{|num, style=\default|
        ^NoteDict.create(style).notes.findKeyForValue(num);
    }

    init{

        var smallnames, bignames;
        var smallnotes, bignotes;

        notes= ();

        smallnames= [ 'c', 'd', 'e', 'f', 'g', 'a', 'b' ];
        bignames= [ 'C', 'D', 'E', 'F', 'G', 'A', 'B' ];

        smallnotes= ['#', 'b'].collect{|mod| 
            smallnames.stutter.collect{|note, i|
                if(mod=='#', {
                    if(i.even, { note }, { (note++mod).asSymbol });
                }, {
                    if(i.odd, { note }, { (note++mod).asSymbol });
                });
            }.removeAll(['b#', 'e#', 'cb', 'fb'])
        };

        bignotes= ['#', 'b'].collect{|mod| 
            bignames.stutter.collect{|note, i|
                if(mod=='#', {
                    if(i.even, { note }, { (note++mod).asSymbol });
                }, {
                    if(i.odd, { note }, { (note++mod).asSymbol });
                });
            }.removeAll(['B#', 'E#', 'Cb', 'Fb'])
        };

        // small > seventh-lined octave
        small= 8.collect{|oct|
            smallnotes.collect{|row| 
                row.collect{|note|
                    if(oct!=0, { (note++oct).asSymbol }, { note });
                }
            }
        }.flatten;

        // big < subsubcontra octave
        big= 4.collect{|oct|
            bignotes.collect{|row| 
                row.collect{|note|
                    if(oct!=0, { (note++oct).asSymbol }, { note });
                }
            }
        }.flatten;

        // MIDI octave numbers
        numbered= 11.collect{|oct| 
            bignotes.collect{|row| 
                row.collect{|note| ((note++(oct-1)).asSymbol) }
            } 
        }.flatten;
    }

    makeDict{|style=\default, output|

        var soct= 0;
        var boct= 0;
        var noct= -1;

        switch(style)

        {\default} { 
            small.do{|row, x|
                if(x.even, { soct= soct+1 });
                row.do{|name, num| 
                    notes.put(name, (num+36)+(12*soct)); 
                }
            };
            big.do{|row, x|
                if(x.even, { boct= boct+1 });
                row.do{|name, num| 
                    notes.put(name, (num+48)-(12*boct)); 
                }
            };
        }
        {\MIDI} { 
            numbered.do{|row, x|
                if(x.even, { noct= noct+1 });
                row.do{|name, num| notes.put(name, num+(12*noct));
                }
            };
        }
        { "wrong style".throw }
        ;

        // convert to frequencies
        if(output==\freq, {
            notes= notes.collect{|num| num.midicps }
        });
    }

    noteDict{
        ^notes;
    }

    at{|note|
        ^this.noteDict[note]
    }

    chord{|notes|
        ^this.noteDict.atAll(notes);
    }

    freq{|note|
        ^this[note].midicps
    }
}
