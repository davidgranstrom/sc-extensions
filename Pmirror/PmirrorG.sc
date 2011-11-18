// a mirrored geometrical series
// dg 09/11/2011 
// inspired from a workshop with sergio luque

PmirrorG : Pattern {

    var <>size, <>start, <>grow, <>length=inf;
    
    *new{|... args|
        ^super.newCopyArgs(*args)
    }
            
    storeArgs{ ^[size,start,grow,length] }

    embedInStream{|inval|

        var inSize=  size.asStream;
        var inStart= start.asStream;
        var inGrow=  grow.asStream;

        length.value(inval).do{
            var arr= Array.geom(
                                inSize.next(inval), 
                                inStart.next(inval), 
                                inGrow.next(inval)
            );
        arr.mirror1.do{|x| inval= x.yield };
        }
        ^inval;
    }                                 
}
