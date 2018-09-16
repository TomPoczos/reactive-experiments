package com.tompoczos.vertxpipes01;

import com.sun.tools.javac.util.List;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import java.lang.reflect.Array;

public class Pipes2 {

  class SequentialPipe {

    public void apply(Message message) {
      Single.just(message)
        .map(m -> m)
        .map(new Processor1())
        .map(new Processor2())
        .map(new Processor3())
        .subscribe(/*other verticle and error handler*/);
    }
  }

  class ScatterGatherPipe {

    public void Process(Message message) {

      final Single<Message> rxMsg = Single.just(message);

      final Single<MessagePart1> subResult1 = rxMsg.map(new Processor4()); // rxMsg.map(new Processor4()).subscribeOn(Schedulers.newThread())
      final Single<MessagePart2> subResult2 = rxMsg.map(new Processor5()); // if you'd trade the creation of new threads to get guaranteed parallelism
      final Single<MessagePart3> subResult3 = rxMsg.map(new Processor6());

      // message::new can be expanded to the boilerplatey
      // (msgPart1, msgPart2, msgPart3) -> new Message(msgPart1, msgPart2, msgPart3)
      Single.zip(subResult1, subResult2, subResult3, Message::new)
        .subscribe(/*other verticle and error handler*/);
    }
  }

  class MessagePart1{}
  class MessagePart2{}
  class MessagePart3{}

  class Message {

    public Message() { }

    public Message(MessagePart1 messagePart1, MessagePart2 messagePart2, MessagePart3 messagePart3) {
      this.messagePart1 = messagePart1;
      this.messagePart2 = messagePart2;
      this.messagePart3 = messagePart3;
    }

    public MessagePart1 messagePart1;
    public MessagePart2 messagePart2;
    public MessagePart3 messagePart3;
  }

  class Processor1 implements io.reactivex.functions.Function<Message, Message> {

    @Override
    public Message apply(Message message) throws Exception {
      return message;
    }
  }

  class Processor2 implements io.reactivex.functions.Function<Message, Message> {

    @Override
    public Message apply(Message message) throws Exception {
      return message;
    }
  }

  class Processor3 implements io.reactivex.functions.Function<Message, Message> {

    @Override
    public Message apply(Message message) throws Exception {
      return message;
    }
  }

  class Processor4 implements io.reactivex.functions.Function<Message, MessagePart1> {

    @Override
    public MessagePart1 apply(Message message) throws Exception {
      return new MessagePart1();
    }
  }

  class Processor5 implements io.reactivex.functions.Function<Message, MessagePart2> {

    @Override
    public MessagePart2 apply(Message message) throws Exception {
      return new MessagePart2();
    }
  }

  class Processor6 implements io.reactivex.functions.Function<Message, MessagePart3> {

    @Override
    public MessagePart3 apply(Message message) throws Exception {
      return new MessagePart3();
    }
  }

}
