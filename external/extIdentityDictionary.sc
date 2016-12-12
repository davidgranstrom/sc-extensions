+ IdentityDictionary {
    asInheritedKeyValuePairs {
        var input, output, parents;

        input = this.copy;
        parents = List[];

        // get parent dictionaries
        while { input.notNil; } {
            input = input.parent;
            input !? {
                parents.add(input);
            };
        };

        output = ();
        // start from top parent
        parents.reverse.do {|parent|
            parent.keysValuesDo {|key, value|
                output.put(key, value);
            };
        };

        // merge with input values
        this.keysValuesDo {|key, value|
            output.put(key, value);
        };

        ^output.asKeyValuePairs;
    }
}
