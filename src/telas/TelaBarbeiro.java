/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;
import conexao_db.ModuloConexao;
import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ninguem
 */
public class TelaBarbeiro extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    /**
     * Creates new form TelaBarbeiro
     */
    public TelaBarbeiro() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    private void adicionar(){
        String sql = "insert into tb_barbeiros (nome_barbeiro, email, tel, login, senha, perfil) values(?, ?, ?, ?, ? ,?)";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            pst.setString(2, txtEmail.getText());
            
            pst.setString(3, txtTel.getText());
            String telefone = txtTel.getText().replaceAll("[^0-9]", ""); 
            
            pst.setString(4, txtLogin.getText());
   
            //String telefone = txtTel.getText().replaceAll("[^0-9]", ""); 
            
            String senha = new String(txtSenha.getPassword());
            String conf_senha = new String(txtCsenha.getPassword());
            pst.setString(5, senha);
            pst.setString(6, cbPerfil.getSelectedItem().toString());
            
            
            if(txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || telefone.isEmpty() || txtLogin.getText().isEmpty() || senha.isEmpty() || conf_senha.isEmpty() || cbPerfil.getSelectedItem().toString().equals("Selecione")){
                JOptionPane.showMessageDialog(null, "[ERRO] Preencha todos os campos");
            }
            
            else if(!senha.equals(conf_senha)){
                JOptionPane.showMessageDialog(null, "Campo CONFIRMAR SENHA diferente de Campo SENHA");
            }
            
            else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Barbeiro adicionado com sucesso!");
                    limparCampos();
                    pesquisarBarbeiro();
                }else{
                    JOptionPane.showMessageDialog(null, "[ERRO] não foi possivel adicionar o barbeiro");
                    
                }
            }
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    public void alterar(){
        String sql = "UPDATE tb_barbeiros set nome_barbeiro = ?, email = ?, tel = ?, login = ?, senha = ?, perfil = ? WHERE id_barbeiro = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            pst.setString(2, txtEmail.getText());
            
            pst.setString(3, txtTel.getText());
            
            //Limpando a formatação do Jformatted do Telefone para testes posteriormente.
            String telefone = txtTel.getText().replaceAll("[^0-9]", "");
            
            pst.setString(4, txtLogin.getText());
            
            //Pegando a senha
            String senha = new String(txtSenha.getPassword());
            String conf_senha = new String(txtCsenha.getPassword());
            pst.setString(5, senha);
            
            pst.setString(6, cbPerfil.getSelectedItem().toString());
            
            //Pegando o ID do barbeiro proveniente das funções pesquisarBarbeiro() e setarCampos();
            pst.setString(7, lblId.getText());
            
            if(txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || telefone.isEmpty() || senha.isEmpty() || conf_senha.isEmpty()){
                JOptionPane.showMessageDialog(null, "[ERRO] Preencha todos os Campos");
            }
            
            else if(!senha.equals(conf_senha)){
                JOptionPane.showMessageDialog(null, "Campo CONFIRMAR SENHA diferente de Campo SENHA");
            }
            
            else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!!");
                    limparCampos();
                    pesquisarBarbeiro();
                    
                }else{
                    JOptionPane.showMessageDialog(null, "[ERRO] Não foi possivel atualizar os dados");
                    
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void deletar(){
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar este barbeiro?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){
            String sql = "DELETE FROM tb_barbeiros WHERE id_barbeiro = ?";
            
            try {
                pst = conexao.prepareStatement(sql);
                
                pst.setString(1, lblId.getText());
                
                int deletado = pst.executeUpdate();
                
                if(deletado > 0){
                    JOptionPane.showMessageDialog(null, "Barbeiro deletado com sucesso!!");
                    limparCampos();
                    pesquisarBarbeiro();
                    
                }else{
                    JOptionPane.showMessageDialog(null, "[ERRO] Não foi possivel deletar o barbeiro.");
                }
            } catch (Exception e) {
            }
            
            
        }
    }
    
    public void pesquisarBarbeiro(){
        String sql = "SELECT id_barbeiro as ID, nome_barbeiro as nome, email as email, tel as telefone, login as login, senha as senha, perfil as perfil FROM tb_barbeiros WHERE nome_barbeiro like ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            
            tblBarbeiros.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void setarCampos(){
        int linha_selecionada = tblBarbeiros.getSelectedRow();
        
        lblId.setText(tblBarbeiros.getModel().getValueAt(linha_selecionada, 0).toString());
        txtNome.setText(tblBarbeiros.getModel().getValueAt(linha_selecionada, 1).toString());
        txtEmail.setText(tblBarbeiros.getModel().getValueAt(linha_selecionada, 2).toString());
        txtTel.setText(tblBarbeiros.getModel().getValueAt(linha_selecionada, 3).toString());
        txtLogin.setText(tblBarbeiros.getModel().getValueAt(linha_selecionada, 4).toString());
        txtSenha.setText(tblBarbeiros.getModel().getValueAt(linha_selecionada, 5).toString());
        txtCsenha.setText(tblBarbeiros.getModel().getValueAt(linha_selecionada, 5).toString());
        cbPerfil.setSelectedItem(tblBarbeiros.getModel().getValueAt(linha_selecionada, 6).toString());
        
        
    }
    
    public void limparCampos(){
        lblId.setText("##");
        txtNome.setText(null);
        txtEmail.setText(null);
        txtTel.setText(null);
        txtLogin.setText(null);
        txtSenha.setText(null);
        txtCsenha.setText(null);
        cbPerfil.setSelectedItem("Selecione");
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
        jLabel6 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTel = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        txtCsenha = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBarbeiros = new javax.swing.JTable();
        btnCadastrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cbPerfil = new javax.swing.JComboBox<>();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(747, 661));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BARBEIROS");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 38)); // NOI18N
        jLabel3.setText("BARBEIROS");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/gang750.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 0, 760, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("NOME:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("EMAIL:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("TEL:");

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        try {
            txtTel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ##### - ####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("ID:");

        lblId.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblId.setText("##");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("PESQUISAR BARBEIRO:");

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("LOGIN:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("SENHA:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("CONFIRMAR SENHA:");

        txtLogin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCsenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tblBarbeiros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBarbeiros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBarbeirosMouseClicked(evt);
            }
        });
        tblBarbeiros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblBarbeirosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblBarbeiros);

        btnCadastrar.setBackground(new java.awt.Color(0, 204, 51));
        btnCadastrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCadastrar.setText("CADASTRAR");
        btnCadastrar.setPreferredSize(new java.awt.Dimension(125, 50));
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(255, 153, 0));
        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEditar.setText("EDITAR");
        btnEditar.setPreferredSize(new java.awt.Dimension(125, 50));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 0, 0));
        btnExcluir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExcluir.setText("EXCLUIR");
        btnExcluir.setPreferredSize(new java.awt.Dimension(125, 50));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("PERFIL:");

        cbPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "user", "admin" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblId))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNome)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(29, 29, 29)
                                        .addComponent(jLabel10))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(38, 38, 38)
                                        .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCsenha, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(348, 348, 348)
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                            .addComponent(txtSenha)))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblId))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtCsenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        setBounds(0, 0, 747, 661);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void tblBarbeirosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblBarbeirosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBarbeirosKeyReleased

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisarBarbeiro();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblBarbeirosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBarbeirosMouseClicked
        // TODO add your handling code here:
        setarCampos();
    }//GEN-LAST:event_tblBarbeirosMouseClicked

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JComboBox<String> cbPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel lblId;
    private javax.swing.JTable tblBarbeiros;
    private javax.swing.JPasswordField txtCsenha;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JFormattedTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
