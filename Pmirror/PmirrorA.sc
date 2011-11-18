// a mirrored arithmetic series
// dg 18/11/2011 

PmirrorA : Pattern {

    var <>size, <>start=0.0, <>step=1.0, <>length=inf;

    *new{|... args|
        ^super.newCopyArgs(*args)
    }
            
    storeArgs{ ^[size,start,step,length] }

    embedInStream{|inval|

        var inSize=  size.asStream;
        var inStart= start.asStream;
        var inStep=  step.asStream;

        length.value(inval).do{
            var arr= Array.series(
                               inSize.next(inval), 
                               inStart.next(inval),
                               inStep.next(inval)
            );
        arr.mirror1.do{|x| inval= x.yield };
        }
        ^inval;
    }                                 
}
