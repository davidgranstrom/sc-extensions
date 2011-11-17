// David Granstrom 09/11/2011 

PmirFib : Pattern {

    var <>size, <>step=0.0, <>start=1.0, <>length=inf;

    *new{|... args|
        ^super.newCopyArgs(*args)
    }
            
    storeArgs{ ^[size,start,step,length] }

    embedInStream{|inval|

        var inSize=  size.asStream;
        var inStep=  step.asStream;
        var inStart= start.asStream;

        length.value(inval).do{
            var arr= Array.fib(
                               inSize.next(inval), 
                               inStep.next(inval),
                               inStart.next(inval) 
            );
            arr.mirror1.do{|x| inval= x.yield };
        }
        ^inval;
    }                                 
}
