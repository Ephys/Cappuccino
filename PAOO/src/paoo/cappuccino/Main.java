package paoo.cappuccino;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.config.IAppConfig;
import paoo.cappuccino.core.config.PropertiesConfig;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.ihm.ConnectionView;
import paoo.cappuccino.util.exception.FatalException;
import paoo.cappuccino.util.hasher.IStringHasher;
import paoo.cappuccino.util.hasher.StringHasher;
import paoo.cappuccino.util.hasher.pbkdf2.Pbkdf2Hasher;

public class Main {

  private static final File RESOURCES_FOLDER = new File("lib");

  public static void main(String[] args) {
    AppContext appContext = new AppContext("Cappuccino", "1.0.0");
    DependencyInjector injector = configureApp(appContext);

    // TODO: change this by GuiManager once it has been pulled
    ConnectionView view = (ConnectionView) injector.buildDependency(ConnectionView.class);
  }

  public static DependencyInjector configureApp(AppContext appContext) {
    IAppConfig appConfig =
        makeConfig(appContext.getProfile() + ".properties",
            appContext.getProfileType() == AppContext.Profile.DEV);

    DependencyInjector injector = new DependencyInjector(appConfig);
    injector.setDependency(AppContext.class, appContext);
    injector.setDependency(IAppConfig.class, appConfig);

    try {
      IStringHasher hasher = new StringHasher();
      hasher.addHashAlgorithm(new Pbkdf2Hasher(appConfig.getInt("pbkdf2_iterations")));

      injector.setDependency(IStringHasher.class, hasher);
    } catch (NoSuchAlgorithmException e) {
      throw new FatalException("Could not fetch the hashing algorithm", e);
    }

    return injector;
  }

  private static IAppConfig makeConfig(final String filename, final boolean debug) {
    if (!RESOURCES_FOLDER.exists() && !RESOURCES_FOLDER.mkdirs()) {
      throw new FatalException("Could not make config directory "
          + RESOURCES_FOLDER.getAbsolutePath());
    }

    final File configFile = new File(RESOURCES_FOLDER, filename);

    try {
      return new PropertiesConfig(configFile, debug);
    } catch (IOException e) {
      throw new FatalException("Could not load the config file " + configFile.getAbsolutePath(), e);
    }
  }
}
