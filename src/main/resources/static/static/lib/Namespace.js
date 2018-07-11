var NS = (function (global) {
	"use strict";
	
	// empty object to verify namespaced objects
	function Namespace() {}
	
	return function (namespace) {
		var chunks = namespace.split('.'),
			lastObjRef = global,
			node = null,
			i;
			
		// iterate over chunks
		for (i = 0; i < chunks.length; ++i) {
			// set current
			node = chunks[i];
			
			// exist?
			if (typeof lastObjRef[node] !== 'undefined') {
				// must be a namespace
				if (lastObjRef[node] instanceof Namespace === false) {
					throw new Error('Non Namespace Object exists, cannot create namespace.');
				}
				
				// assign
				lastObjRef = lastObjRef[node];
				// next iteration
				continue;
			}
			
			// create new object
			lastObjRef[node] = new Namespace();
			
			// assign new last object reference
			lastObjRef = lastObjRef[node];
		}
		
		// return reference to prevent double typing
		return lastObjRef;
	};
	
}(this));