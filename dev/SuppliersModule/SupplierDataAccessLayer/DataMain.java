package SuppliersModule.SupplierDataAccessLayer;
import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.ContractDAO;
import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.SupplierDAO;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;
import SuppliersModule.SuppliersBusinessLayer.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataMain {
    public static void main(String[] args) throws Exception {
        SupplierDAO supplierDAO = new SupplierDAO();
        Supplier newSupplier = supplierDAO.get(10);
        List<SupplierProduct> products = new ArrayList<>();
        products.add(new SupplierProduct(101,100, 3));
        products.add(new SupplierProduct(102,200, 4));
        HashMap<Integer, List<int[]>> discounts = new HashMap<>();
        List<int[]> list1 = new ArrayList<>();
        list1.add(new int[]{2,1});
        list1.add(new int[]{10,6});
        list1.add(new int[]{30,40});
        discounts.put(3,list1);
        List<int[]> list2 = new ArrayList<>();
        list2.add(new int[]{20,10});
        list2.add(new int[]{100,60});
        discounts.put(4,list2);
        ContractDAO contractDAO = new ContractDAO();
        Contract newContract = new Contract(newSupplier, products, discounts, list1);
        contractDAO.create(newContract);/*
        Contract contract = contractDAO.get(1);
        System.out.println(contract.getSupplier().getName());
        System.out.println(contract.getProductsIds());
        HashMap <Integer, List<int[]>> map = contract.getDiscounts();
        for (int key: map.keySet()){
            System.out.print(key + ": ");
            List<int[]> value =  map.get(key);
            for(int[] pair: value){
                System.out.print(Arrays.toString(pair));
            }
            System.out.println();
        }*/
    }
}
