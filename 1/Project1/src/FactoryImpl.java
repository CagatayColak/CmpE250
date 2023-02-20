import java.util.HashSet;
import java.util.NoSuchElementException;

public class FactoryImpl implements Factory {
    private Holder first = null;
    private Holder last = null;
    private int size = 0;

    @Override
    public void addFirst(Product product) {
        if (size == 0) {
            first = new Holder(null, product, null);
            last = first;
        } else {
            Holder temp = new Holder(null, product, first);
            first.setPreviousHolder(temp);
            first = temp;
        }

        size++;
    }


    @Override
    public void addLast(Product product) {
        if (last == null) {
            last = new Holder(null, product, null);
            first = last;
        } else {
            Holder temp = new Holder(last, product, null);
            last.setNextHolder(temp);
            last = temp;
        }

        size++;
    }


    @Override
    public Product removeFirst() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException("Factory is empty.");

        if (size == 1) {
            Product temp = first.getProduct();
            first = null;
            last = null;
            size = 0;
            return temp;
        }

        Holder temp = first;
        first = first.getNextHolder();
        size--;

        return temp.getProduct();
    }


    @Override
    public Product removeLast() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException("Factory is empty.");

        if (size == 1) {
            Product temp = first.getProduct();
            first = null;
            last = null;
            size = 0;
            return temp;
        }

        Product product = last.getProduct();
        Holder temp = last.getPreviousHolder();
        temp.setNextHolder(null);
        last = temp;
        size--;

        return product;
    }


    @Override
    public Product find(int id) throws NoSuchElementException {
        Holder temp = first;

        while (temp != null) {
            if (temp.getProduct().getId() == id) {
                return temp.getProduct();
            } else {
                temp = temp.getNextHolder();
            }
        } 

        throw new NoSuchElementException("Product not found.");
    }


    @Override
    public Product update(int id, Integer value) throws NoSuchElementException {
        Holder temp = first;

        while (temp != null) {
            if (temp.getProduct().getId() == id) {
                Product newProduct = new Product(id, temp.getProduct().getValue());
                temp.getProduct().setValue(value);
                return newProduct;
            } else {
                temp = temp.getNextHolder();
            }
        } 

        throw new NoSuchElementException("Product not found.");
    }


    @Override
    public Product get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");

        Holder temp = first;
        int i = 0;

        while (temp != null) {
            if (i == index) {
                return temp.getProduct();
            } else {
                temp = temp.getNextHolder();
                i++;
            }
        }

        return null;
    }


    @Override
    public void add(int index, Product product) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds.");

        Holder temp = first;
        int i = 0;

        if (index == 0) {
            addFirst(product);
            return;
        }

        if (index == size) {
            addLast(product);
            return;
        }

        while (temp != null) {
            if (i == index) {
                Holder prevTemp = temp.getPreviousHolder();
                Holder newTemp = new Holder(prevTemp, product, temp);

                prevTemp.setNextHolder(newTemp);
                temp.setPreviousHolder(newTemp);
                size++;
                break;
            } else {
                temp = temp.getNextHolder();
                i++;
            }
        }
    }


    @Override
    public Product removeIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");

        Holder temp = first;
        int i = 0;
        Product p = null;

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        while (temp != null) {
            if (i == index) {
                Holder prevTemp = temp.getPreviousHolder();
                Holder nextTemp = temp.getNextHolder();

                prevTemp.setNextHolder(nextTemp);
                nextTemp.setPreviousHolder(prevTemp);
                size--;
                p = temp.getProduct();
                break;
            } else {
                temp = temp.getNextHolder();
                i++;
            }
        }

        return p;
    }


    @Override
    public Product removeProduct(int value) throws NoSuchElementException {
        Holder temp = first;
        Product p = null;

        while (temp != null) {
            if (temp.getProduct().getValue() == value) {
                if (temp.equals(first))
                    p = removeFirst();
                else if (temp.equals(last))
                    p = removeLast();
                else {
                    Holder prevTemp = temp.getPreviousHolder();
                    Holder nextTemp = temp.getNextHolder();

                    prevTemp.setNextHolder(nextTemp);
                    nextTemp.setPreviousHolder(prevTemp);
                    p = temp.getProduct();
                    size--;

                }
                break;
            } else {
                temp = temp.getNextHolder();
            }
        } 

        if (p == null)
            throw new NoSuchElementException("Product not found.");

        return p;
    }

    @Override
    public int filterDuplicates() {
        HashSet<Integer> productValueSet = new HashSet<Integer>();

        Holder temp = first;
        int count = 0;

        while (temp != null) {
            if (productValueSet.contains(temp.getProduct().getValue())) {
                Holder prevTemp = temp.getPreviousHolder();
                Holder nextTemp = temp.getNextHolder();

                if (nextTemp != null) {
                    prevTemp.setNextHolder(nextTemp);
                    nextTemp.setPreviousHolder(prevTemp);
                } else {
                    prevTemp.setNextHolder(null);
                    last = prevTemp;
                }
                
                size--;
                count++;
            } else {
                productValueSet.add(temp.getProduct().getValue());
            }

            temp = temp.getNextHolder();
        }

        return count;
    }

    @Override
    public void reverse() {
        Holder firstRef = first;
        Holder temp = first;
        Holder nextTemp, prevTemp;

        while (temp != null) {
            nextTemp = temp.getNextHolder();
            prevTemp = temp.getPreviousHolder();
            temp.setNextHolder(prevTemp);
            temp.setPreviousHolder(nextTemp);

            temp = nextTemp;
        }
        first = last;
        last = firstRef;
    }

    public String toString() {
        if (size == 0) return "{}";

        // Holder temp = first;
        // String s = "{";
        // while (temp != null) {
        //     s += temp.getProduct().toString();
        //     if (temp.getNextHolder() != null) s += ",";
        //     temp = temp.getNextHolder();
        // }

        // return s + "}";

        StringBuilder sb = new StringBuilder();
        
        sb.append("{");
        for (Holder temp = first; temp != null; temp = temp.getNextHolder()) {
            sb.append(temp.getProduct().toString());
            sb.append(",");    
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append("}");
    
        return sb.toString();
    }
}