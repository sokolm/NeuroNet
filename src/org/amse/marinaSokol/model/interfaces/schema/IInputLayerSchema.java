package org.amse.marinaSokol.model.interfaces.schema;

public interface IInputLayerSchema extends IUsualLayerSchema {
    /**
     * Устанавливает имя файла, из которого будут считываться входы
     * @param fileName - имя файла
     * */
    void setFileName(String fileName);

    /**
     * Возвращает имя файла, из которого будут считываться входы
     * @return fileName - имя файла
     * */
    String getFileName();

}
