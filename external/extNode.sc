+ Node {

    moveUp {
        OSCFunc({|msg|
            var cmd, argnodeID, parent, prev, next, isGroup, head, tail;
            #cmd, argnodeID, parent, prev, next, isGroup, head, tail = msg;
                if(prev != -1) {
                    server.sendMsg(18, nodeID, prev); // "/n_before"
                }
            },
            '/n_info',
            server.addr
        ).oneShot;

        server.sendMsg(46, nodeID); // "/n_query"
    }

    moveDown {
        OSCFunc({|msg|
            var cmd, argnodeID, parent, prev, next, isGroup, head, tail;
            #cmd, argnodeID, parent, prev, next, isGroup, head, tail = msg;
                if(next != -1) {
                    server.sendMsg(19, nodeID, next); // "/n_after"
                }
            },
            '/n_info',
            server.addr
        ).oneShot;

        server.sendMsg(46, nodeID); // "/n_query"
    }
}
