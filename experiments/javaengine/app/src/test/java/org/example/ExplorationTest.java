package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import com.dylibso.chicory.runtime.ExportFunction;
import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.runtime.Memory;
import com.dylibso.chicory.runtime.Store;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.wasm.types.ValType;
import com.dylibso.chicory.wasm.types.Value;
import com.dylibso.chicory.wasm.types.FunctionType;

public class ExplorationTest {
    @Test void wasmFactorialReturnsCorrectly() {
        // point this to your path on disk
        var module = Parser.parse(new File("src/test/resources/wasm/factorial.wasm"));
        Instance instance = Instance.builder(module).build();
        ExportFunction iterFact = instance.export("iterFact");
        var result = iterFact.apply(5)[0];

        assertEquals(120, result);
    }

    @Test void wasmCountsVowels() {
        Instance instance = Instance.builder(Parser.parse(new File("src/test/resources/wasm/count_vowels.wasm"))).build();
        ExportFunction countVowels = instance.export("count_vowels");

        ExportFunction alloc = instance.export("alloc");
        ExportFunction dealloc = instance.export("dealloc");

        Memory memory = instance.memory();
        String message = "Hello, World!";
        int len = message.getBytes().length;

        // allocate {len} bytes of memory, this returns a pointer to that memory
        int ptr = (int) alloc.apply(len)[0];

        // We can now write the message to the module's memory:
        memory.writeString(ptr, message);

        var result = countVowels.apply(ptr, len)[0];
        dealloc.apply(ptr, len);
        assertEquals(3L, result); // 3 vowels in Hello, World!
    }

    //@Test 
    void wasmInfiniteLoopCanBeInterrupted() {
        Instance instance = Instance.builder(Parser.parse(new File("src/test/resources/wasm/infinite-loop.wasm"))).build();
        ExportFunction function = instance.export("run");

        var thread = new Thread() {
            @Override
            public void run() {
                function.apply();
            }
        };
        thread.start();
        boolean interrupted = false;
        try {
            Thread.sleep(200);
            thread.interrupt();
        } catch (InterruptedException e) {
            interrupted = true;
        }
        //assertTrue(interrupted);
        assertTrue(!thread.isAlive());
    }

    @Test void wasmConsoleLogWorks() {
        var func = new HostFunction(
            "console",
            "log",
            FunctionType.of(
                List.of(ValType.I32, ValType.I32),
                List.of()
            ),
            (Instance instance, long... args) -> { // decompiled is: console_log(13, 0);
                var len = (int) args[0];
                var offset = (int) args[1];
                var message = instance.memory().readString(offset, len);
                System.out.println(message);
                return null;
            });

        // instantiate the store
        var store = new Store();
        // registers `console.log` in the store
        store.addFunction(func);

        // create a named `instance` with name `logger`
        var instance = store.instantiate("logger", Parser.parse(new File("src/test/resources/wasm/logger.wasm")));
    }

    
}
