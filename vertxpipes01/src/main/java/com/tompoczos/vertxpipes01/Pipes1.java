package com.tompoczos.vertxpipes01;

import io.reactivex.Single;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.lang.reactivex.RxGen;

public class Pipes1 extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    startFuture.complete();
  }

  private final Processor processor1 = Processor.createProxy(vertx, "processors/1");
  private final Processor processor2 = Processor.createProxy(vertx, "processors/2");
  private final Processor processor3 = Processor.createProxy(vertx, "processors/3");
  private final Processor processor4 = Processor.createProxy(vertx, "processors/4");
  private final Processor processor5 = Processor.createProxy(vertx, "processors/5");

  @ProxyGen
  public interface Processor {

    @Fluent
    Processor process(JsonObject m, Handler<AsyncResult<JsonObject>> resultHandler);

    static Processor createProxy(Vertx vertx, String addr) {
      return new com.tompoczos.vertxpipes01.ProcessorVertxEBProxy(vertx, addr);
    }
  }

  class Pipe1 implements Processor {

    @Override
    public Processor process(JsonObject m, Handler<AsyncResult<JsonObject>> resultHandler) {

      processor1.process(m, res -> {
        if (res.succeeded()) {
          processor2.process(res.result(), res2 -> {
            if (res2.succeeded()) {
              processor3.process(res2.result(), res3 -> {
                if (res3.succeeded()) {
                  processor4.process(res3.result(), res4 -> {
                    if (res4.succeeded()) {
                      processor5.process(res4.result(), res5 -> {
                        if (res5.succeeded()) {
                          // exiting pipe
                        }
                      });
                    } else {
                      // log the error from the right res...
                    }
                  });
                } else {
                  // log the error from the right res...
                }
              });
            } else {
              // log the error from the right res...
            }
          });
        } else {
          // log the error from the right res...
        }
      });
      return this;
    }
  }

  class Pipe2 implements Processor {

    Handler<AsyncResult<JsonObject>> myHandler(AsyncResult<JsonObject> res, Handler<AsyncResult<JsonObject>> actualHandler) {
      if (res.succeeded())
        return actualHandler;
      else
        // log error
        return (AsyncResult<JsonObject> res2) -> {  };
    }

    @Override
    public Processor process(JsonObject m, Handler<AsyncResult<JsonObject>> resultHandler) {

      // shorter, less indentation, but even more error prone

      processor1.process(m, res -> {
        processor2.process(res.result(), myHandler(res, res2 -> {
          processor3.process(res2.result(), myHandler(res2, res3 -> {
            processor4.process(res3.result(), myHandler(res3, res4 -> {
              processor5.process(res4.result(), myHandler(res4, res5 -> {
                // exiting pipe
              }));
            }));
          }));
        }));
      });
      return this;
    }
  }

  class Processor1 implements Processor {

    @Override
    public Processor process(JsonObject m, Handler<AsyncResult<JsonObject>> resultHandler) {
      return this;
    }
  }

  class Processor2 implements Processor {

    @Override
    public Processor process(JsonObject m, Handler<AsyncResult<JsonObject>> resultHandler) {
      return this;
    }
  }

  class Processor3 implements Processor {

    @Override
    public Processor process(JsonObject m, Handler<AsyncResult<JsonObject>> resultHandler) {
      return this;
    }
  }

  class Processor4 implements Processor {

    @Override
    public Processor process(JsonObject m, Handler<AsyncResult<JsonObject>> resultHandler) {
      return this;
    }
  }

  class Processor5 implements Processor {

    @Override
    public Processor process(JsonObject m, Handler<AsyncResult<JsonObject>> resultHandler) {
      return this;
    }
  }
}
