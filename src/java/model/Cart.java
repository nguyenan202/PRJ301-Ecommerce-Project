package model;

import dal.ProductDAO;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Item> list;

    public Cart() {
        list = new ArrayList<>();
    }

    public Cart(List<Item> list) {
        this.list = list;
    }

    

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
    
    public Item getItemByProductId(int id) {
        for (Item item:this.list) {
            if (item.getProduct().getId() == id) return item;
        }
        return null;
    }
    
    public int addToCart(Item item) throws Exception {
        ProductDAO pd = new ProductDAO();
        
        //Item already exist in cart ==> update quantity or remove from cart
        if (getItemByProductId(item.getProduct().getId()) != null) {
            Item i = getItemByProductId(item.getProduct().getId());
            int unitInStock = pd.getUnitInStockByProductId(item.getProduct().getId());
            
            //Not Enouht Produt ==> return -1
            if (item.getQuantity()+i.getQuantity() > unitInStock || unitInStock == 0) return -1;
            
            i.setQuantity(i.getQuantity()+item.getQuantity());
            i.setPrice(i.getPrice()*i.getQuantity());
        } else {
            this.list.add(item);
        }
        
        return 1;
    }
    
    public Double getTotalPrice() {
        double total = 0;
        
        for (Item item:this.list) {
            total += item.getPrice();
        }
        
        return total;
    }
    
    public void removeItemByProductId(int id) {
        if(this.getItemByProductId(id) != null) this.list.remove(this.getItemByProductId(id));
    }
}
