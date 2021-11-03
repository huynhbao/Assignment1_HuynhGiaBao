/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.AdminDAO;
import dtos.ProductDTO;
import dtos.UserDTO;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import utils.MyConstants;

/**
 *
 * @author HuynhBao
 */
@WebServlet(name = "AdminCreateProductController", urlPatterns = {"/AdminCreateProductController"})
public class AdminCreateProductController extends HttpServlet {

    private static final String ERROR = "AdminManageProductsController";
    private static final String SUCCESS = "AdminManageProductsController";

    private static final Logger LOGGER = Logger.getLogger(AdminCreateProductController.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USERDTO");

            if (user == null) {
                url = MyConstants.LOGIN;
            } else if (!MyConstants.AD_ROLE.equals(user.getRoleID())) {
                url = ERROR;
                request.setAttribute("MSG", "You do not have permission!");
            } else {
                boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
                if (isMultiPart) {
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List items = upload.parseRequest(new ServletRequestContext(request));

                    Iterator iter = items.iterator();

                    Hashtable params = new Hashtable();

                    String itemName = "";
                    FileItem itemUpload = null;

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                            params.put(item.getFieldName(), item.getString("UTF-8"));
                        } else {
                            itemName = item.getName();
                            itemUpload = item;
                        }
                    }

                    String productID = (String) params.get("txtProductID");
                    String name = (String) params.get("txtProductName");
                    String description = (String) params.get("txtDescription");
                    String quantityStr = (String) params.get("txtQuantity");
                    String priceStr = (String) params.get("txtPrice");
                    String expiryDateStr = (String) params.get("txtExpiryDate");
                    String category = (String) params.get("cbCategory");

                    int quantity = 0;
                    float price = 0;
                    Date expiryDate = null;
                    boolean error = false;

                    if ("".equals(productID)) {
                        request.setAttribute("ERROR_PRODUCTID", "ProductID cannot be empty");
                        error = true;
                    } else {
                        if (productID.length() > 20) {
                            request.setAttribute("ERROR_PRODUCTID", "ProductID less or equal 20 characters");
                            error = true;
                        }
                    }
                    if ("".equals(name)) {
                        request.setAttribute("ERROR_NAME", "Product Name cannot be empty");
                        error = true;
                    }
                    if ("".equals(description)) {
                        request.setAttribute("ERROR_DESCRIPTION", "Description cannot be empty");
                        error = true;
                    }
                    if ("".equals(quantityStr)) {
                        request.setAttribute("ERROR_QUANTITY", "Quanity cannot be empty");
                        error = true;
                    } else {
                        try {
                            quantity = Integer.parseInt(quantityStr);
                        } catch (NumberFormatException e) {
                            request.setAttribute("ERROR_QUANTITY", "Quantity must be integer!");
                            error = true;
                        }
                    }
                    if ("".equals(priceStr)) {
                        request.setAttribute("ERROR_PRICE", "Price cannot be empty");
                        error = true;
                    } else {
                        try {
                            price = Float.parseFloat(priceStr);
                        } catch (NumberFormatException e) {
                            request.setAttribute("ERROR_PRICE", "Price must be float!");
                            error = true;
                        }
                    }

                    if ("".equals(expiryDateStr)) {
                        request.setAttribute("ERROR_DATE", "Expiry date cannot be empty");
                        error = true;
                    } else {
                        try {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            expiryDate = df.parse(expiryDateStr);
                        } catch (NumberFormatException e) {
                            request.setAttribute("ERROR_DATE", "Expiry date wrong format. It should be yyyy-MM-dd");
                            error = true;
                        }
                    }

                    if ("".equals(category)) {
                        request.setAttribute("ERROR_CATEGORY", "Category cannot be empty");
                        error = true;
                    }

                    if (error) {
                        request.setAttribute("CREATE_ERROR", error);
                        url = String.format(ERROR + "?txtProductID=%s&txtProductName=%s&txtDescription=%s&txtQuantity=%s&txtPrice=%s&cbCategory=%s", productID, name, description, quantityStr, priceStr, category);
                    } else {
                        try {
                            AdminDAO dao = new AdminDAO();
                            ProductDTO product = new ProductDTO(productID, name, quantity, price, null, expiryDate, null, description, category, null, true);
                            if (!"".equals(itemName)) {

                                String fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                                String storePath = "images\\" + params.get("cbCategory") + "\\" + fileName;

                                String realPath = getServletContext().getRealPath("/") + storePath;

                                File savedFile = new File(realPath);
                                if (!savedFile.getParentFile().exists()) {
                                    savedFile.getParentFile().mkdirs();
                                    itemUpload.write(savedFile);
                                } else {
                                    if (savedFile.exists()) {
                                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                        fileName = itemName.substring(itemName.lastIndexOf("\\") + 1) + "_" + timestamp.getTime();
                                        storePath = "images\\" + params.get("cbCategory") + "\\" + fileName;
                                        realPath = getServletContext().getRealPath("/") + storePath;
                                        File savedFileNew = new File(realPath);
                                        itemUpload.write(savedFileNew);
                                    } else {
                                        itemUpload.write(savedFile);
                                    }
                                }

                                product.setImgLink(storePath);

                            } else {
                                product.setImgLink(MyConstants.defaultImgLink);
                            }

                            dao.createProduct(product);
                            request.setAttribute("CREATE_MSG", "Create Product successful!");
                            url = SUCCESS;
                        } catch (Exception e) {
                            if (e.toString().contains("duplicate")) {
                                request.setAttribute("ERROR_PRODUCTID", "Product ID is existed!");
                                request.setAttribute("CREATE_ERROR", true);
                                url = String.format(ERROR + "?txtProductID=%s&txtProductName=%s&txtDescription=%s&txtQuantity=%s&txtPrice=%s&cbCategory=%s", productID, name, description, quantityStr, priceStr, category);
                            } else {
                                LOGGER.error(e.toString());
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
