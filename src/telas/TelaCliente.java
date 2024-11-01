/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;
import java.sql.*;
import conexao_db.ModuloConexao;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

//A linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ninguem
 */
public class TelaCliente extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        
        conexao = ModuloConexao.conector();
    }
    
    
    
    public void adicionar(){
        String id_barbeiro = TelaPrincipal.lblId_b.getText();
        
         String sql = "INSERT INTO tb_clientes (id_barbeiro, nome_cliente, email, tel, servico, valor, data_c, hora) "
               + "VALUES (?, ?, ?, ?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?)";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, id_barbeiro);
            pst.setString(2, txtNome.getText());
            pst.setString(3, txtEmail.getText());
            pst.setString(4, txtTel.getText());
            pst.setString(5, txtServico.getText());
            pst.setString(6, txtValor.getText());
            pst.setString(7, txtData.getText());
            pst.setString(8, txtHorario.getText());
            
            /*Limpa os caracteres da mascara do JFormatted e insere em variaveis
            para testar campos em branco posteriormente*/
            
            String telefone = txtTel.getText().replaceAll("[^0-9]", ""); 
            
            String data = txtData.getText().replaceAll("[^0-9]", ""); 
            
            String horario = txtHorario.getText().replaceAll("[^0-9]", "");
            
            //Teste para verificar campos em branco
            if(txtNome.getText().isEmpty() || telefone.isEmpty() || 
               txtServico.getText().isEmpty()|| data.isEmpty()|| horario.isEmpty() || txtValor.getText().isEmpty()){
               
                JOptionPane.showMessageDialog(null, "[ERRO] Preencha todos os *Campos Obrigatórios.");
                
            }else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Cliente agendado com sucesso!!");
                    limparCampos();
                    pesquisarCliente();
                }else{
                    JOptionPane.showMessageDialog(null, "[ERRO] Não foi possivel agendar o cliente.");
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[ERRO] Erro no Banco de Dados");
            System.out.println(e);
        }
           
    }
    
    
    private void alterar(){
        String sql = "UPDATE tb_clientes set nome_cliente = ?, email = ?, tel = ?, servico = ?, valor = ?, data_c = STR_TO_DATE(?, '%d/%m/%Y'), hora = ? WHERE id_cliente = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            pst.setString(2, txtEmail.getText());
            pst.setString(3, txtTel.getText());
            pst.setString(4, txtServico.getText());
            pst.setString(5, txtValor.getText());
            pst.setString(6, txtData.getText());
            pst.setString(7, txtHorario.getText());
            pst.setString(8, lblIdCliente.getText());
            
            /*Limpa os caracteres da mascara do JFormatted e insere em variaveis
            para testar campos em branco posteriormente*/
            
            String telefone = txtTel.getText().replaceAll("[^0-9]", ""); 
            
            String data = txtData.getText().replaceAll("[^0-9]", ""); 
            
            String horario = txtHorario.getText().replaceAll("[^0-9]", "");
            
            //Teste para verificar campos em branco
            if(txtNome.getText().isEmpty() || telefone.isEmpty() || 
               txtServico.getText().isEmpty()|| data.isEmpty()|| horario.isEmpty() || txtValor.getText().isEmpty()){
               
                JOptionPane.showMessageDialog(null, "[ERRO] Preencha todos os *Campos Obrigatórios.");
                
            }else{
                int adicionado = pst.executeUpdate();
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Dados do Usúario de ID: " + lblIdCliente.getText() + " alterados com sucesso!");
                    limparCampos();
                    pesquisarCliente();
                }else{
                    JOptionPane.showMessageDialog(null, "[ERRO], não foi possível alterar os dados.");
                }
            }
           
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    
    
    public void deletar(){
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja EXCLUIR este agendamento?", "Atenção", JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){
            String sql = "DELETE FROM tb_clientes WHERE id_cliente = ?";
            
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, lblIdCliente.getText());
                
                int deletado = pst.executeUpdate();
                
                if(deletado > 0){
                    JOptionPane.showMessageDialog(null, "Agendamento excluído com sucesso!!");
                    limparCampos();
                    pesquisarCliente();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    
    
    //Método para pesquisar clientes pelo nome com filtro
    
   private void pesquisarCliente(){
        String sql = " SELECT id_cliente as ID, id_barbeiro as ID_Barbeiro, nome_cliente as nome,email as email, tel as tel, servico as serviço,valor as valor, marcacao as marcação, data_c as data, hora as horário FROM tb_clientes WHERE nome_cliente like ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1, txtPesquisar.getText() +  "%");
            rs = pst.executeQuery();
            
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        }
    }
   
   
   public void setarCampos(){
       int linha_selecionada = tblClientes.getSelectedRow();
       
       lblIdCliente.setText(tblClientes.getModel().getValueAt(linha_selecionada, 0).toString());
       txtNome.setText(tblClientes.getModel().getValueAt(linha_selecionada, 2).toString());
       txtEmail.setText(tblClientes.getModel().getValueAt(linha_selecionada, 3).toString());
       txtTel.setText(tblClientes.getModel().getValueAt(linha_selecionada, 4).toString());
       txtServico.setText(tblClientes.getModel().getValueAt(linha_selecionada, 5).toString());
       txtValor.setText(tblClientes.getModel().getValueAt(linha_selecionada, 6).toString());
       //txtData.setText(tblClientes.getModel().getValueAt(linha_selecionada, 8).toString());
       
       // Formatar a data para o formato dd/MM/yyyy
    try {
        String dataBanco = tblClientes.getModel().getValueAt(linha_selecionada, 8).toString();
        SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

        String dataFormatada = formatoDesejado.format(formatoBanco.parse(dataBanco));
        txtData.setText(dataFormatada);
    } catch (Exception e) {
        e.printStackTrace();
    }
       
       txtHorario.setText(tblClientes.getModel().getValueAt(linha_selecionada, 9).toString());
   }
   
   public void limparCampos(){
       lblIdCliente.setText("##");
       txtNome.setText(null);
       txtEmail.setText(null);
       txtTel.setText(null);
       txtServico.setText(null);
       txtData.setText(null);
       txtHorario.setText(null);
       txtValor.setText(null);
       txtPesquisar.setText(null);
       
   }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtServico = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnAgendar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtHorario = new javax.swing.JFormattedTextField();
        txtTel = new javax.swing.JFormattedTextField();
        txtData = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtValor = new javax.swing.JTextField();
        lblIdCliente = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setClosable(true);
        setPreferredSize(new java.awt.Dimension(747, 661));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CLIENTES");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 49)); // NOI18N
        jLabel3.setText("CLIENTES");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barbearia_fundo_200_747.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 140));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("ID:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("*NOME: ");

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("*SERVIÇO: ");

        txtServico.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("*VALOR: ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("*DATA: ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("*HORÁRIO: ");

        btnAgendar.setBackground(new java.awt.Color(0, 204, 51));
        btnAgendar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAgendar.setText("Agendar");
        btnAgendar.setPreferredSize(new java.awt.Dimension(50, 25));
        btnAgendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(255, 153, 0));
        btnEditar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setPreferredSize(new java.awt.Dimension(50, 25));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 0, 0));
        btnExcluir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setPreferredSize(new java.awt.Dimension(50, 25));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("EMAIL:");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("*TELEFONE:");

        txtPesquisar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("PESQUISAR CLIENTE: ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("*CAMPOS OBRIGÁTORIOS");

        try {
            txtHorario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHorario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        try {
            txtTel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex ){
                return false;
            }
        };
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        txtValor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblIdCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIdCliente.setText("##");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(45, 45, 45))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIdCliente))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(txtEmail)
                                    .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtServico, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(btnAgendar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblIdCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(txtHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        setBounds(0, 0, 747, 661);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnAgendarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisarCliente();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        setarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgendar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIdCliente;
    private javax.swing.JTable tblClientes;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JFormattedTextField txtHorario;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtServico;
    private javax.swing.JFormattedTextField txtTel;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
