package cc.koosha.nettyfunctional.hook;

import cc.koosha.nettyfunctional.matched.MatchedInboundHandler;
import cc.koosha.nettyfunctional.nettyfunctions.Matcher;
import io.netty.channel.ChannelHandlerContext;
import lombok.NonNull;


public abstract class RemovedInboundHook<T> extends MatchedInboundHandler<T> {

    protected RemovedInboundHook() {
    }

    protected RemovedInboundHook(@NonNull final Class<?> clazz) {

        super(clazz);
    }

    protected RemovedInboundHook(@NonNull final Matcher matcher) {

        super(matcher);
    }


    @Override
    protected final void unsupportedMsg(final ChannelHandlerContext ctx,
                                        final Object msg) {

        ctx.fireChannelRead(msg);
    }

    @Override
    protected void read0(final ChannelHandlerContext ctx,
                         final T msg) throws Exception {

        this.read1(ctx, msg);
        ctx.fireChannelRead(msg);
        ctx.pipeline().remove(this);
    }

    protected abstract void read1(ChannelHandlerContext ctx, T msg)
            throws Exception;

}
