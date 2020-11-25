package es.ucm.gdv.engine;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase auxiliar para almacenar objetos.
 * @param <T> El tipo de los objetos
 */
public class Pool<T> {
    public interface PoolObjectFactory<T> {
        public T createObject();
    }

    /**
     * Lista de objetos libres
     */
    private final List<T> freeObjects;
    /**
     * La factoría que se usará para crear nuevos objetos
     */
    private final PoolObjectFactory<T> factory;
    /**
     * Tamaño máximo de la lista de objetos libres
     */
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<T>(maxSize);
    }

    /**
     * Devuelve un objeto del pool de objetos.
     * Si no hay objetos libres, lo crea (siempre que haya espacio).
     * @return Un objeto del pool de objetos
     */
    public T getObject() {
        T object = null;
        if (freeObjects.size() == 0)
            object = factory.createObject();
        else
            object = freeObjects.remove(freeObjects.size() - 1);
        return object;
    }

    /**
     * Libera un objeto del pool, guardándolo en la cola de libres.
     * @param object El objeto a liberar
     */
    public void free(T object) {
        if (freeObjects.size() < maxSize)
            freeObjects.add(object);
    }

}
