package theintelligentminds.messenger;

import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import javassist.bytecode.Descriptor;

public class AndroidFriendlyFeature implements Feature{

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new AbstractBinder() {
            @Override
            protected void configure() {
                addUnbindFilter(new Filter() {
                    @Override
                    public boolean matches(org.glassfish.hk2.api.Descriptor d) {
                        String implClass = d.getImplementation();
                        return implClass.startsWith(
                                "org.glassfish.jersey.message.internal.DataSource")
                                || implClass.startsWith(
                                "org.glassfish.jersey.message.internal.RenderedImage");
                    }
                });
            }
        });
        return true;
    }
}