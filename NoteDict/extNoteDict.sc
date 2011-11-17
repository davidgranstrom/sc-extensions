
+ SimpleNumber {
    asNote{|style=\default|
        ^NoteDict.numberToNote(this, style)
    }
}

+ SequenceableCollection {
    asNote{|style=\default| 
        ^this.collect{|x| x.asNote(style) } 
    }
}
