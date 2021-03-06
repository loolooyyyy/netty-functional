package io.koosha.nettyfunctional.hook;

import io.koosha.nettyfunctional.matched.MatchedInboundHandler;
import io.koosha.nettyfunctional.nettyfunctions.Matcher;
import io.netty.channel.ChannelHandlerContext;


public abstract class RemovedInboundHook<I> extends MatchedInboundHandler<I> {

    protected RemovedInboundHook() {
        super();
    }

    protected RemovedInboundHook(final Class<?> clazz) {
        super(clazz);
    }

    protected RemovedInboundHook(final Matcher matcher) {
        super(matcher);
    }


    @Override
    protected final void unsupportedMsg(final ChannelHandlerContext ctx,
                                        final Object msg) {
        ctx.fireChannelRead(msg);
    }

    @Override
    protected void read0(final ChannelHandlerContext ctx,
                         final I msg) throws Exception {
        this.read1(ctx, msg);
        ctx.pipeline().remove(this);
        ctx.fireChannelRead(msg);
    }

    protected abstract void read1(ChannelHandlerContext ctx, I msg)
            throws Exception;

}
