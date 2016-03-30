package cc.koosha.nettyfunctional.hook;

import cc.koosha.nettyfunctional.matched.MatchedInboundHandler;
import cc.koosha.nettyfunctional.nettyfunctions.Matcher;
import io.netty.channel.ChannelHandlerContext;
import lombok.NonNull;


public abstract class RemovedInboundSink<T> extends MatchedInboundHandler<T> {

    public RemovedInboundSink() {
    }

    public RemovedInboundSink(@NonNull final Matcher matcher) {
        super(matcher);
    }

    @Override
    protected final void unsupportedMsg(final ChannelHandlerContext ctx,
                                        final Object msg) {

        ctx.fireChannelRead(msg);
    }

    @Override
    protected final void read0(final ChannelHandlerContext ctx,
                               final T read) throws Exception {

        this.read1(ctx, read);
        ctx.pipeline().remove(this);
    }

    protected abstract void read1(ChannelHandlerContext ctx, T read)
            throws Exception;

}
