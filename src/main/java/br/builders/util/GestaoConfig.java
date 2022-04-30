package br.builders.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

public class GestaoConfig {

    private static final ResourceBundle RESOURCE_CONFIG = ResourceBundle.getBundle("application");

    /**
     * Construtor privado para garantir o Singleton.
     *
     */
    private GestaoConfig() {

    }

    /**
     * Retorna a configuração segundo a chave informada.
     *
     * @param chave
     * @return
     */
    public static String getConfig(final String chave) {
        return RESOURCE_CONFIG.getString(chave);
    }

    /**
     * Retorna o profile em que a aplicação está sendo execultada.
     *
     * @return
     */
    private static String getProfile() {
        return getConfig("br.com.cadastro.profile");
    }

    /**
     * Verifica se a aplicação está sendo executado com o profile de
     * Desenvolvimento.
     *
     * @return
     */
    public static boolean isProfileDev() {
        String profile = getProfile();
        return !Util.isBlank(profile)
                && Constantes.DEV.equals(profile.trim().toLowerCase());
    }

    /**
     * Verifica se a aplicação está sendo executado com o profile de
     * Homologação.
     *
     * @return
     */
    public static boolean isProfileHmg() {
        String profile = getProfile();
        return !Util.isBlank(profile)
                && Constantes.HMG.equals(profile.trim().toLowerCase());
    }

    /**
     * Verifica se a aplicação está sendo executado com o profile de Produção.
     *
     * @return
     */
    public static boolean isProfilePrd() {
        String profile = getProfile();
        return !Util.isBlank(profile)
                && Constantes.PRD.equals(profile.trim().toLowerCase());
    }


    public static String getTemplateRelatorio(final String nome) throws IOException {
        String template = "";
        String repositorio = getConfig("template.relatorios");

        if (!Util.isBlank(repositorio) && !Util.isBlank(nome)) {
            StringBuilder url = new StringBuilder(repositorio.trim());
            url.append(repositorio.trim().endsWith("\\") || repositorio.trim().endsWith("/") ? "" : File.separator);
            url.append(nome.trim());

            InputStream input = GestaoConfig.class.getResourceAsStream(url.toString());
            template = IOUtils.toString(input, "ISO-8859-1");
        }
        return template;
    }

    public static String getUrlTempate(final String nome) {
        String template = "";
        String repositorio = getConfig("template.relatorios");

        if (!Util.isBlank(repositorio) && !Util.isBlank(nome)) {
            StringBuilder url = new StringBuilder(repositorio.trim());
            url.append(repositorio.trim().endsWith("\\") || repositorio.trim().endsWith("/") ? "" : File.separator);
            url.append(nome.trim());

            return  url.toString();
        }

        return null;
    }
}
