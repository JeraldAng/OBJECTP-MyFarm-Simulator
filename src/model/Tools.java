package model;

/**
 * This class will only store the functionality and the name of the "Tools"
 */
public abstract class Tools {
    public String name;
    public String function;

    /**
     * This will only store the name and the function of the specific tool
     * @param name Name of the Tool
     * @param function Function of the Tool
     */
    public Tools(String name, String function) {
        this.name = name;
        this.function = function;
    }

    public String getName()
    {
        return name;
    }

    public String getFunction ()
    {
        return function;
    }
}
