// David Granstrom 09/11/2011 

Pmirror : Pattern {

    var <>list, <>length=inf;
    
    *new{|... args|
        ^super.newCopyArgs(*args)
    }
            
    storeArgs{ ^[list,length] }

    embedInStream{|inval|

        length.value(inval).do{
            var arr= list.value(inval);
            arr.mirror1.do{|x| inval= x.yield };
        }
        ^inval;
    }
}
