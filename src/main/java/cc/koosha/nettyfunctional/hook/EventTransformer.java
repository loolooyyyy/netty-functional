package cc.koosha.nettyfunctional.hook;

import cc.koosha.nettyfunctional.nettyfunctions.Matcher;
import cc.koosha.nettyfunctional.matched.MatchedEventHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.NonNull;


@ChannelHandler.Sharable
public abstract class EventTransformer<T> extends MatchedEventHandler<T> {

    protected EventTransformer() {
    }

    protected EventTransformer(@NonNull final Class<?> type) {

        super(type);
    }

    protected EventTransformer(@NonNull final Matcher matcher) {

        super(matcher);
    }


    @Override
    protected final void unsupportedEvent(final ChannelHandlerContext ctx,
                                          final Object event) {

        ctx.fireUserEventTriggered(event);
    }

    @Override
    protected final void event0(final ChannelHandlerContext ctx,
                                final T event) throws Exception {

        final Object result = this.event1(ctx, event);

        if(result != null)
            ctx.fireUserEventTriggered(result);
    }

    protected abstract Object event1(ChannelHandlerContext ctx, T event)
            throws Exception;

}
