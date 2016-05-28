import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import ru.svrw.logged.Log;
import ru.svrw.sql.ds.BufferPool;
import ru.svrw.sql.rowset.CachedRowSet;
import ru.svrw.util.Properties;

import java.text.*;

public class WindowExpress extends JApplet implements ActionListener {
	JTextField dorText, year, month;
	JButton doIt;
	JCheckBox cbBSLP, cbBSLSP, cbDSLP, cbDSLSP, cbPass, cbTransp, cbFSLP, cbFSLSP, cbILNP, cbILNSP, cbNLNP, cbNLNSP, cbPLNP, cbPLNSP, cbPP, cbPPRZD, 
		cbPR, cbPRRZD, cbRLNP, cbRLNSP, cbRRUP, cbRRUSP, cbRSNP, cbRSNSP, cbGLNP, cbGLNSP, cbROMP, cbROMSP, cbBSLP1, cbBSLSP1, cbDSLP1, cbDSLSP1, 
		cbFSLP1, cbFSLSP1, cbILNP1, cbILNSP1, cbNLNP1, cbNLNSP1, cbPLNP1, cbPLNSP1, cbPP1, cbPPRZD1, cbPR1, cbPRRZD1, cbRLNP1, cbRLNSP1, cbRRUP1, 
		cbRRUSP1, cbRSNP1, cbRSNSP1, cbGLNP1, cbGLNSP1, cbROMP1, cbROMSP1;
	JLabel resLab;
	int doroga;
	
	NumberFormat nf;
	public void init() {
		try { 
			SwingUtilities.invokeAndWait(new Runnable() { 
				public void run() { 
					makeGUI(); // ������������� GUI 
				} 
			});
		}
		catch(Exception ���) { 
			System.out.println ("��� 't create because of " + ���); 
			// System.out.println ("���������� ������� ��-�� " + ���);
		}
	}
	
	// ������������� � �������������� GUI. 
	private void makeGUI() { 
		// ���������� �������� ����������. 
		GridBagLayout gbag = new GridBagLayout(); 
		GridBagConstraints gbc = new GridBagConstraints(); 
		setLayout(gbag); 
		JLabel heading = new JLabel("�������������� ������ �� ������� txt � xls"); 
		JLabel dorLab = new JLabel("����� ������: "); 
		dorText = new JTextField(10); 
		JLabel monthLab = new JLabel("�����: "); 
		month = new JTextField(3); 
		JLabel yearLab = new JLabel("���: "); 
		year = new JTextField(5); 
		cbBSLP = new JCheckBox("BSLP");
		cbBSLSP = new JCheckBox("BSLSP");
		cbDSLP = new JCheckBox("DSLP");
		cbDSLSP = new JCheckBox("DSLSP");
		cbFSLP = new JCheckBox("FSLP");
		cbFSLSP = new JCheckBox("FSLSP");
		cbILNP = new JCheckBox("ILNP");
		cbILNSP = new JCheckBox("ILNSP");
		cbNLNP = new JCheckBox("NLNP");
		cbNLNSP = new JCheckBox("NLNSP");
		cbPLNP = new JCheckBox("PLNP");
		cbPLNSP = new JCheckBox("PLNSP");
		cbPP = new JCheckBox("PP");
		cbPPRZD = new JCheckBox("PPRZD");
		cbPR = new JCheckBox("PR");
		cbPRRZD = new JCheckBox("PRRZD");
		cbRLNP = new JCheckBox("RLNP");
		cbRLNSP = new JCheckBox("RLNSP");
		cbRRUP = new JCheckBox("RRUP");
		cbRRUSP = new JCheckBox("RRUSP");
		cbRSNP = new JCheckBox("RSNP");
		cbRSNSP = new JCheckBox("RSNSP");
		cbGLNP = new JCheckBox("GLNP");
		cbGLNSP = new JCheckBox("GLNSP");
		cbROMP = new JCheckBox("ROMP");
		cbROMSP = new JCheckBox("ROMSP");
		cbBSLP1 = new JCheckBox("BSLP1");
		cbBSLSP1 = new JCheckBox("BSLSP1");
		cbDSLP1 = new JCheckBox("DSLP1");
		cbDSLSP1 = new JCheckBox("DSLSP1");
		cbFSLP1 = new JCheckBox("FSLP1");
		cbFSLSP1 = new JCheckBox("FSLSP1");
		cbILNP1 = new JCheckBox("ILNP1");
		cbILNSP1 = new JCheckBox("ILNSP1");
		cbNLNP1 = new JCheckBox("NLNP1");
		cbNLNSP1 = new JCheckBox("NLNSP1");
		cbPLNP1 = new JCheckBox("PLNP1");
		cbPLNSP1 = new JCheckBox("PLNSP1");
		cbPP1 = new JCheckBox("PP1");
		cbPPRZD1 = new JCheckBox("PPRZD1");
		cbPR1 = new JCheckBox("PR1");
		cbPRRZD1 = new JCheckBox("PRRZD1");
		cbRLNP1 = new JCheckBox("RLNP1");
		cbRLNSP1 = new JCheckBox("RLNSP1");
		cbRRUP1 = new JCheckBox("RRUP1");
		cbRRUSP1 = new JCheckBox("RRUSP1");
		cbRSNP1 = new JCheckBox("RSNP1");
		cbRSNSP1 = new JCheckBox("RSNSP1");
		cbGLNP1 = new JCheckBox("GLNP1");
		cbGLNSP1 = new JCheckBox("GLNSP1");
		cbROMP1 = new JCheckBox("ROMP1");
		cbROMSP1 = new JCheckBox("ROMSP1");
		cbPass = new JCheckBox("����������� ����� �� ����� ����������");
		cbTransp = new JCheckBox("����������� ����� �� ����� ���������");
		doIt = new JButton ("���������"); 
		resLab = new JLabel(""); 
		// ���������� �����. 
		gbc.weighty = 1.0; // ���������� ������, ��� ������� ����� 1 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbc.anchor = GridBagConstraints.NORTH; 
		gbag.setConstraints(heading, gbc); 
		// �������r��� ����������� ����������� ������. 
		gbc.anchor = GridBagConstraints.WEST; 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(dorLab, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(dorText, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(monthLab, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(month, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(yearLab, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(year, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbBSLP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbBSLSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbBSLP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbBSLSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbDSLP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbDSLSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbDSLP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbDSLSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbFSLP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbFSLSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbFSLP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbFSLSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbGLNP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbGLNSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbGLNP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbGLNSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbILNP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbILNSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbILNP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbILNSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbNLNP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbNLNSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbNLNP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbNLNSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbPLNP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbPLNSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbPLNP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbPLNSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbPP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbPPRZD, gbc); 
//		gbc.gridwidth = GridBagConstraints.RELATIVE; 
//		gbag.setConstraints(cbPP1, gbc); 
//		gbc.gridwidth = GridBagConstraints.REMAINDER; 
//		gbag.setConstraints(cbPPRZD1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbPR, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbPRRZD, gbc); 
//		gbc.gridwidth = GridBagConstraints.RELATIVE; 
//		gbag.setConstraints(cbPR1, gbc); 
//		gbc.gridwidth = GridBagConstraints.REMAINDER; 
//		gbag.setConstraints(cbPRRZD1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRLNP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRLNSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRLNP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbRLNSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbROMP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbROMSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbROMP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbROMSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRRUP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRRUSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRRUP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbRRUSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRSNP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRSNSP, gbc); 
		gbc.gridwidth = GridBagConstraints.RELATIVE; 
		gbag.setConstraints(cbRSNP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbRSNSP1, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbPass, gbc); 
		gbc.gridwidth = GridBagConstraints.REMAINDER; 
		gbag.setConstraints(cbTransp, gbc); 
		gbc.anchor = GridBagConstraints.CENTER; 
		gbag.setConstraints(doIt, gbc); 
		gbc.anchor = GridBagConstraints.CENTER; 
		gbag.setConstraints(resLab, gbc); 
		// ��������� ��� ����������. 
		add(heading); 
		add(dorLab); 
		add(dorText); 
		add(monthLab); add(month); add(yearLab); add(year); 
		add(cbBSLP); add(cbBSLSP); add(cbBSLP1); add(cbBSLSP1); 
		add(cbDSLP); add(cbDSLSP); add(cbDSLP1); add(cbDSLSP1); 
		add(cbFSLP); add(cbFSLSP); add(cbFSLP1); add(cbFSLSP1); 
		add(cbGLNP); add(cbGLNSP); add(cbGLNP1); add(cbGLNSP1); 
		add(cbILNP); add(cbILNSP); add(cbILNP1); add(cbILNSP1); 
		add(cbNLNP); add(cbNLNSP); add(cbNLNP1); add(cbNLNSP1); 
		add(cbPLNP); add(cbPLNSP); add(cbPLNP1); add(cbPLNSP1); 
		add(cbPP); add(cbPPRZD); 
//		add(cbPP1); add(cbPPRZD1); 
		add(cbPR); add(cbPRRZD); 
//		add(cbPR1); add(cbPRRZD1); 
		add(cbRLNP); add(cbRLNSP); add(cbRLNP1); add(cbRLNSP1); 
		add(cbROMP); add(cbROMSP); add(cbROMP1); add(cbROMSP1); 
		add(cbRRUP); add(cbRRUSP); add(cbRRUP1); add(cbRRUSP1); 
		add(cbRSNP); add(cbRSNSP); add(cbRSNP1); add(cbRSNSP1); 
		add(cbPass); 
		add(cbTransp); 
		add(doIt); 
		add(resLab); 
		// ��r��������� �� ��������� ������� ��������. 
		dorText.addActionListener(this); 
		month.addActionListener(this); 
		year.addActionListener(this); 
		cbBSLP.addActionListener(this); 
		cbBSLSP.addActionListener(this); 
		cbDSLP.addActionListener(this); 
		cbDSLSP.addActionListener(this); 
		cbFSLP.addActionListener(this); 
		cbFSLSP.addActionListener(this); 
		cbILNP.addActionListener(this); 
		cbILNSP.addActionListener(this); 
		cbNLNP.addActionListener(this); 
		cbNLNSP.addActionListener(this); 
		cbPLNP.addActionListener(this); 
		cbPLNSP.addActionListener(this); 
		cbPP.addActionListener(this); 
		cbPPRZD.addActionListener(this); 
		cbPR.addActionListener(this); 
		cbPRRZD.addActionListener(this); 
		cbRLNP.addActionListener(this); 
		cbRLNSP.addActionListener(this); 
		cbRRUP.addActionListener(this); 
		cbRRUSP.addActionListener(this); 
		cbRSNP.addActionListener(this); 
		cbRSNSP.addActionListener(this); 
		cbGLNP.addActionListener(this); 
		cbGLNSP.addActionListener(this); 
		cbROMP.addActionListener(this); 
		cbROMSP.addActionListener(this); 
		cbBSLP1.addActionListener(this); 
		cbBSLSP1.addActionListener(this); 
		cbDSLP1.addActionListener(this); 
		cbDSLSP1.addActionListener(this); 
		cbFSLP1.addActionListener(this); 
		cbFSLSP1.addActionListener(this); 
		cbILNP1.addActionListener(this); 
		cbILNSP1.addActionListener(this); 
		cbNLNP1.addActionListener(this); 
		cbNLNSP1.addActionListener(this); 
		cbPLNP1.addActionListener(this); 
		cbPLNSP1.addActionListener(this); 
		cbPP1.addActionListener(this); 
		cbPPRZD1.addActionListener(this); 
		cbPR1.addActionListener(this); 
		cbPRRZD1.addActionListener(this); 
		cbRLNP1.addActionListener(this); 
		cbRLNSP1.addActionListener(this); 
		cbRRUP1.addActionListener(this); 
		cbRRUSP1.addActionListener(this); 
		cbRSNP1.addActionListener(this); 
		cbRSNSP1.addActionListener(this); 
		cbGLNP1.addActionListener(this); 
		cbGLNSP1.addActionListener(this); 
		cbROMP1.addActionListener(this); 
		cbROMSP1.addActionListener(this); 
		cbPass.addActionListener(this); 
		cbTransp.addActionListener(this); 
		doIt.addActionListener(this); 
	}
	
	/* ������������ ����� <Enter> � ��������� ���� ��� ������� �� ������ Comp�te. ���������� ���������, ���� ��� ���� ���������. */ 
	public void actionPerformed(ActionEvent ��) {
		String result = ""; 
		String bslp = "";
		String bslsp = "";
		String dslp = "";
		String dslsp = "";
		String fslp = "";
		String fslsp = "";
		String ilnp = "";
		String ilnsp = "";
		String nlnp = "";
		String nlnsp = "";
		String plnp = "";
		String plnsp = "";
		String pp = "";
		String pprzd = "";
		String pr = "";
		String prrzd = "";
		String rlnp = "";
		String rlnsp = "";
		String rrup = "";
		String rrusp = "";
		String rsnp = "";
		String rsnsp = "";
		String glnp = "";
		String glnsp = "";
		String romp = "";
		String romsp = "";
		String bslp1 = "";
		String bslsp1 = "";
		String dslp1 = "";
		String dslsp1 = "";
		String fslp1 = "";
		String fslsp1 = "";
		String ilnp1 = "";
		String ilnsp1 = "";
		String nlnp1 = "";
		String nlnsp1 = "";
		String plnp1 = "";
		String plnsp1 = "";
		String pp1 = "";
		String pprzd1 = "";
		String pr1 = "";
		String prrzd1 = "";
		String rlnp1 = "";
		String rlnsp1 = "";
		String rrup1 = "";
		String rrusp1 = "";
		String rsnp1 = "";
		String rsnsp1 = "";
		String glnp1 = "";
		String glnsp1 = "";
		String romp1 = "";
		String romsp1 = "";
		String pass = "";
		String transp = "";
		String dorStr = dorText.getText(); 
		String mStr = month.getText(); 
		String yStr = year.getText(); 
		if (cbBSLP.isSelected())
			bslp = cbBSLP.getText(); 
		if (cbBSLSP.isSelected())
			bslsp = cbBSLSP.getText(); 
		if (cbDSLP.isSelected())
			dslp = cbDSLP.getText(); 
		if (cbDSLSP.isSelected())
			dslsp = cbDSLSP.getText(); 
		if (cbFSLP.isSelected())
			fslp = cbFSLP.getText(); 
		if (cbFSLSP.isSelected())
			fslsp = cbFSLSP.getText(); 
		if (cbILNP.isSelected())
			ilnp = cbILNP.getText(); 
		if (cbILNSP.isSelected())
			ilnsp = cbILNSP.getText(); 
		if (cbNLNP.isSelected())
			nlnp = cbNLNP.getText(); 
		if (cbNLNSP.isSelected())
			nlnsp = cbNLNSP.getText(); 
		if (cbPLNP.isSelected())
			plnp = cbPLNP.getText(); 
		if (cbPLNSP.isSelected())
			plnsp = cbPLNSP.getText(); 
		if (cbPP.isSelected())
			pp = cbPP.getText(); 
		if (cbPPRZD.isSelected())
			pprzd = cbPPRZD.getText(); 
		if (cbPR.isSelected())
			pr = cbPR.getText(); 
		if (cbPRRZD.isSelected())
			prrzd = cbPRRZD.getText(); 
		if (cbRLNP.isSelected())
			rlnp = cbRLNP.getText(); 
		if (cbRLNSP.isSelected())
			rlnsp = cbRLNSP.getText(); 
		if (cbRRUP.isSelected())
			rrup = cbRRUP.getText(); 
		if (cbRRUSP.isSelected())
			rrusp = cbRRUSP.getText(); 
		if (cbRSNP.isSelected())
			rsnp = cbRSNP.getText(); 
		if (cbRSNSP.isSelected())
			rsnsp = cbRSNSP.getText(); 
		if (cbGLNP.isSelected())
			glnp = cbGLNP.getText(); 
		if (cbGLNSP.isSelected())
			glnsp = cbGLNSP.getText(); 
		if (cbROMP.isSelected())
			romp = cbROMP.getText(); 
		if (cbROMSP.isSelected())
			romsp = cbROMSP.getText(); 
		if (cbBSLP1.isSelected())
			bslp1 = cbBSLP1.getText(); 
		if (cbBSLSP1.isSelected())
			bslsp1 = cbBSLSP1.getText(); 
		if (cbDSLP1.isSelected())
			dslp1 = cbDSLP1.getText(); 
		if (cbDSLSP1.isSelected())
			dslsp1 = cbDSLSP1.getText(); 
		if (cbFSLP1.isSelected())
			fslp1 = cbFSLP1.getText(); 
		if (cbFSLSP1.isSelected())
			fslsp1 = cbFSLSP1.getText(); 
		if (cbILNP1.isSelected())
			ilnp1 = cbILNP1.getText(); 
		if (cbILNSP1.isSelected())
			ilnsp1 = cbILNSP1.getText(); 
		if (cbNLNP1.isSelected())
			nlnp1 = cbNLNP1.getText(); 
		if (cbNLNSP1.isSelected())
			nlnsp1 = cbNLNSP1.getText(); 
		if (cbPLNP1.isSelected())
			plnp1 = cbPLNP1.getText(); 
		if (cbPLNSP1.isSelected())
			plnsp1 = cbPLNSP1.getText(); 
		if (cbPP1.isSelected())
			pp1 = cbPP1.getText(); 
		if (cbPPRZD1.isSelected())
			pprzd1 = cbPPRZD1.getText(); 
		if (cbPR1.isSelected())
			pr1 = cbPR1.getText(); 
		if (cbPRRZD1.isSelected())
			prrzd1 = cbPRRZD1.getText(); 
		if (cbRLNP1.isSelected())
			rlnp1 = cbRLNP1.getText(); 
		if (cbRLNSP1.isSelected())
			rlnsp1 = cbRLNSP1.getText(); 
		if (cbRRUP1.isSelected())
			rrup1 = cbRRUP1.getText(); 
		if (cbRRUSP1.isSelected())
			rrusp1 = cbRRUSP1.getText(); 
		if (cbRSNP1.isSelected())
			rsnp1 = cbRSNP1.getText(); 
		if (cbRSNSP1.isSelected())
			rsnsp1 = cbRSNSP1.getText(); 
		if (cbGLNP1.isSelected())
			glnp1 = cbGLNP1.getText(); 
		if (cbGLNSP1.isSelected())
			glnsp1 = cbGLNSP1.getText(); 
		if (cbROMP1.isSelected())
			romp1 = cbROMP1.getText(); 
		if (cbROMSP1.isSelected())
			romsp1 = cbROMSP1.getText(); 
		if (cbPass.isSelected())
			pass = "yes"; 
		if (cbTransp.isSelected())
			transp = "yes"; 
		try { 
			if ((dorStr.length() != 0) && (mStr.length() != 0) && (yStr.length() != 0)) { 
				Express_ expr = new Express_(dorStr, mStr, yStr);
				result = expr.compute(bslp, bslsp, dslp, dslsp, fslp, fslsp, ilnp, ilnsp, nlnp, nlnsp, plnp, plnsp, pp, pprzd, pr, prrzd, rlnp, rlnsp, rrup, rrusp, rsnp, rsnsp, glnp, glnsp, romp, romsp, bslp1, bslsp1, dslp1, dslsp1, fslp1, fslsp1, ilnp1, ilnsp1, nlnp1, nlnsp1, plnp1, plnsp1, pp1, pprzd1, pr1, prrzd1, rlnp1, rlnsp1, rrup1, rrusp1, rsnp1, rsnsp1, glnp1, glnsp1, romp1, romsp1, pass, transp);
				resLab.setText(result); 
			}
			showStatus (""); // ������� ����� ���������� ��������� �� ������ 
		} catch (NumberFormatException ���) { 
			showStatus("Inva1id Data"); 
			// showStatus("�������� ������"); 
			dorText.setText(""); 
		}
	}
}

class Express_ {
    static BufferedReader br;
    static boolean error = false;
    static BufferedWriter bw;
    private static String s = ""; // ������ ������ ������������ � ��������
    static String n = ""; // ��������� ������ ����������� ��� ������ � ����� � ��������
    
	static String dir = "C:\\Work\\Express\\"; // ����� � ������� txt
	static String year = ""; // ����� � ���, �� ������� ��������������� ������
	static String month = ""; // ����� � ���, �� ������� ��������������� ������
	static int doroga = 0;   // ��� ������, � ������� ��������� ������
	
	Express_(String dor, String m, String y) {
		doroga = Integer.parseInt(dor);
		year = y;
		month = m;
	}

    private static boolean chekError()
    {
        if (error)
        {
            try
            {
                if (br != null) br.close();
                br = null;
                if (bw != null) bw.close();
                bw = null;
            }
            catch (IOException ioe) {}
        }
        return error;
    }
    /* ������ ������� � ������, � ����. ������� (������., ����������), �� ����������, c ��������, � ��������, ������� ����� �������, �������� �����, 
     * ���������� ������� � ������ ����������� � ��, ����� ������� � ������ ������. ����������, � ������������� �����������, 
     * � ����� ����������� � �-� ��������, � ����� ������������� � �-� ��������, � ��������� ������� ����������, ����� ������ (6000-� ��� 7000-�), 
     * ����� ������� � ��������� ���� ���������, ��� ��������� (��������� � �.�.), ��� ���������, ����� ������� � ��������� ������ ���������, 
     * � ���-��� �������, � ������� ����������
     */
    private static void parse(int dat, int dat_st, int st_ot, int st_n, int fare, int summ, int fio, boolean oneFio, String nameFileRead, int countColumns, 
    		int form_tr, int transp, int code_org, int code_podr, int registr, int num_p, int view_doc, String view_tr, String view_pass, int num_doc, 
    		int kol, int num_tr) {
        String[] strArr = "".split("");
        String strTmp = "";
        
        try
        {
	    // �������� ��� ��������� � ������������, ��������� ������� �� ����� ������� ����� � ��� � ����� ����� ����� ��������� ��� ���� � ����
	       Log log = new Log("${pathProject}/conf/BufferPool.properties");
	       BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
	    // �������� ��� ���� ������ ����������� ��� ����� �������� ����� �� ��������� ���� ������ ����� ������ � ����� ���������
	    // ����� � ��� � ����� ����� ����� � ��������� ����������� � SQL
	       Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
	    // ������ ��� ������ � ������ �������, ����� ������ ��������� SQL ����� �� ������� SQL � ����. ��� ���������� ������ ������� ��. ����� �������.
	       Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
	       CachedRowSet crs = new CachedRowSet(log2);
	       String sql = prop.getString("sql.select");       

	       boolean first = true;
            int numStrCell = 0;
            int for_ind = 0;
            int tra_ind = 0;
            int org_ind = 0;
            int pod_ind = 0;
            int reg_ind = 0;
            int vd_ind = 0;
            int vt_ind = 0;
            String[] st = "".split("");
            String sta_otp = "";
            String sta_n = "";
            int vp_ind = 0;
            String famOne = "";
            String famTwo = "";
            int kolRow = 0;
            
            if (br != null)
            while ((strTmp = br.readLine()) != null) // ��������� ������ �� ����� (���� �� ������ �� ����)
            {
                numStrCell++;
                if (strTmp.indexOf("-0") > -1) {
                	strTmp = strTmp.replaceAll(" -0", " !-");
                }
                if (strTmp.indexOf("--") >= 0) kolRow++;
                if (kolRow >= 3) {
                if (strTmp.indexOf("--") > -1) // ����� � ������ ������ ��������� �������� ��������� (�� ���� �������� ������� ������� ����� ��������� � ������)
                {
                    numStrCell = 0;
                    if (strArr.length == countColumns + 1)
                    {
                    	strArr[0] = null;
                    	for (int i = 1; i < strArr.length; i++) { // ���������� �������� ��������� ������� � ���� ������
                            if (strArr[i].length() == -1 || strArr[i].trim().equals("") || strArr[i].trim().equals("''"))
                                strArr[i] = null;
                            if (i == form_tr) {
                       	 		sql = prop.getString("sql.sel_FormTr");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	// ���� ������ ���������� ������� � �� ���� ������, �� ... 
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		// ���� ���������� ������� ������ 0, �� ...
                           	 		if (crs.size() > 0) {
                           	 			while (crs.next()) for_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                           	 			sql = prop.getString("sql.sel_maxFormTr");
                           	 			if (sql == null) {
                           	 				log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                           	 				break;
                           	 			}
                           	 			crs.setSQL(sql);
                           	 			// ���� ������ ���������� ������� � �� ���� ������, �� ... 
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) for_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_FormTr");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   			// ��������� SQL � ���������            
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(for_ind);
                                   			// �������� SQL, ���� ���� ������, �� ��������� ������
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   	         log2.error(crs.getException());
                                   	         break;
                                   			}
                                   	 	}
                                   	 	// ���� ���� ������, �� ����������� 
                                   	 	else {
                                  	         log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	// ���� ���� ������, �� ����������� 
                           	 	else {
                          	         log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }
                            
                            if (i == transp) {
                       	 		sql = prop.getString("sql.sel_transp");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                           	 	if ((strArr[i].indexOf("6000") > -1) || (strArr[i].indexOf("7000") > -1))
                           	 		crs.addParameter((strArr[i].substring(0, (strArr[i].trim()).length()-4)).trim());
                           	 	else
                           	 		crs.addParameter(strArr[i].trim());
                           	 	if (strArr[i].indexOf("6000") > -1)
                           	 		num_p = 6000;
                           	 	if (strArr[i].indexOf("7000") > -1)
                           	 		num_p = 7000;
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 	    while (crs.next()) tra_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxTransp");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) tra_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_transp");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                       	 	if (strArr[i].indexOf("6000") > -1)
                                       	 		crs.addParameter((strArr[i].substring(0, (strArr[i].trim()).length()-4)).trim());
                                       	 	else
                                       	 		crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(tra_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == code_org) {
                       	 		sql = prop.getString("sql.sel_codeOrg");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) org_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxCodeOrg");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) org_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_codeOrg");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(org_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == code_podr) {
                       	 		sql = prop.getString("sql.sel_codePodr");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) pod_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxCodePodr");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) pod_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_codePodr");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(pod_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == registr) {
                       	 		sql = prop.getString("sql.sel_registr");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) reg_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxRegistr");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) reg_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_registr");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(reg_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == view_doc) {
                       	 		sql = prop.getString("sql.sel_viewDoc");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) vd_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxViewDoc");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) vd_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_viewDoc");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(vd_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == st_ot) {
                             	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                               			while (crs.next()) sta_otp = crs.getString(1);
                               		}
                               		else {
                            			sta_otp = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                      	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                    		log2.error(crs.getException());
                                    		s += " " + sql + " " + st[0].trim() + ", " + st[1];
                                    		break;
                                     	}
                               	 	}
                               	 }
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }
                            if (i == st_n) {
                               	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                             			while (crs.next()) sta_n = crs.getString(1);
                               		}
                               		else {
                               			sta_n = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                       	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                       		log2.error(crs.getException());
                                       		break;
                                       	}
                                 	}
                                }
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == num_tr) {
                            	strArr[i] = "'" + strArr[i] + "'";
                            }
                            
                            if (i == fio) {
                            	if (!oneFio) {
                            		while (strArr[i].indexOf("  ") > -1) {
                            			strArr[i] = strArr[i].replaceAll("  ", " ");
                            		}
                            		String[] z = strArr[i].split(";");
                            		if (strArr[i].indexOf("=") > -1) {
                            			famOne = z[0].trim();
                            			famTwo = "'" + z[1].trim() + "'";
                            		}
                            		else {
                            			famOne = z[0].substring(0, z[0].indexOf(" ")).trim() + " " + z[0].substring(z[0].indexOf(" ") + 1);
                            			famTwo = "'" + z[1].substring(0, z[1].indexOf(" ")).trim() + " " + z[1].substring(z[1].indexOf(" ") + 1) + "'";
                            		}
                            	}
                            	else {
                            		if (strArr[i].indexOf("=") > -1) 
                            			famOne = strArr[i].trim();
                            		else 
                            			famOne = strArr[i].substring(0,strArr[i].length() - 2).trim() + " " + strArr[i].substring(strArr[i].length() - 2);
                            		famTwo = null;
                            	}
                            }
                    	}
                    	
               	 		sql = prop.getString("sql.sel_viewTr");
               	 		if (sql == null) {
               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
               	 			break;
               	 		}
                   	 	crs.setSQL(sql);
               			crs.addParameter(view_tr);
                       	if (bp.executeQuery(crs) && !crs.isError())
                       	{
                       		if (crs.size() > 0) {
                       			while (crs.next()) vt_ind = crs.getInteger(1);
                       		}
                       		else {
                       	 		sql = prop.getString("sql.sel_maxViewTr");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		while (crs.next()) vt_ind = crs.getInteger(1,0) + 1;
                           	 		sql = prop.getString("sql.ins_viewTr");
                           	 		if (sql == null) {
                           	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                           	 			break;
                           	 		}
                           	 		crs.setSQL(sql);
                           			crs.addParameter(view_tr);
                           			crs.addParameter(vt_ind);
                           			if (!bp.updateQuery(crs) || crs.isError())
                           			{
                           				log2.error(crs.getException());
                           				break;
                           			}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                       		}
                       	}
                   	 	else {
                   	 		log2.error(crs.getException());
                   	 		break;
                   	 	}
                        
               	 		sql = prop.getString("sql.sel_viewPass");
               	 		if (sql == null) {
               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
               	 			break;
               	 		}
                   	 	crs.setSQL(sql);
               			crs.addParameter(view_pass);
                       	if (bp.executeQuery(crs) && !crs.isError())
                       	{
                       		if (crs.size() > 0) {
                       			while (crs.next()) vp_ind = crs.getInteger(1);
                       		}
                       		else {
                       	 		sql = prop.getString("sql.sel_maxViewPass");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                              	if (bp.executeQuery(crs) && !crs.isError())
                              	{
                              		while (crs.next()) vp_ind = crs.getInteger(1,0) + 1;
                           	 		sql = prop.getString("sql.ins_viewPass");
                           	 		if (sql == null) {
                           	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                           	 			break;
                           	 		}
                           	 		crs.setSQL(sql);
                              		crs.addParameter(view_pass);
                              		crs.addParameter(vp_ind);
                              		if (!bp.updateQuery(crs) || crs.isError())
                              		{
                              			log2.error(crs.getException());
                              			break;
                              		}
                              	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                       		}
                       	}
                   	 	else {
                   	 		log2.error(crs.getException());
                   	 		break;
                   	 	}
                        
                       	if (registr == 0) {
                       		if (num_p == 7000) 
                       			reg_ind = 2; 
                       	}
                       	n = strArr[num_doc];
                       	if (vd_ind == 0)
                       		if (reg_ind == 0) 
                             	crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
                             		sta_n + ", " + vt_ind + ",  null, " + num_p + ", null, " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
                             		vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
                             		famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + doroga + ")");
                       		else 
                       			crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
                    				sta_n + ", " + vt_ind + ", null, " + num_p + ", " + reg_ind + ", " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
                     				vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
                     				famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + doroga + ")");
                       	else
                       		if (reg_ind == 0) 
                             	crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
                             		sta_n + ", " + vt_ind + ", " + vd_ind + ", " + num_p + ", null, " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
                             		vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
                             		famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + doroga + ")");
                       		else
                       			crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
                       				sta_n + ", " + vt_ind + ", " + vd_ind + ", " + num_p + ", " + reg_ind + ", " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
                       				vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
                       				famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + doroga + ")");

                        if (!bp.updateQuery(crs) || crs.isError())
                        {
                        	log2.error(crs.getException());
                        	log2.debug("SQL:\n\t" + crs.getSQLAndParameters());
                        	break;
                        }
                    }
                    strArr = "".split("");
                	first = true;
                	continue;
                }
                if (first) // ������ ������ ����� "--"
                {
                    first = false;
                    strArr = strTmp.split("!"); // ������ �������� ����� "!"
                }
                else // ����������� ���� � ������ ��� "--"
                {
                    String[] tmp = strTmp.split("!"); // ������ �������� ����� "!"
                    for (int i = 0; i < tmp.length; i++)
                    {
//                    	if ((i == fio) && (numStrCell == 2) && (strArr[i].length() == 15) && (strArr[i].indexOf(" ") < 0))
//                    		strArr[i] += " ";
                    	if ((i == fio) && (numStrCell == 3))
                    		strArr[i] += ";";
                        if (numStrCell == 2) {
                            if (st_ot == i || st_n == i)
                                strArr[i] = strArr[i] + ";"; // ���������� ����������� ����� ��������� � ������� ������� (��� ��������� � ����� �������� �������)
                            if (view_doc == i)
                            	strArr[i] = strArr[i].trim() + " ";
                        }
                    	if ((i == fio) && ((numStrCell == 2) || (numStrCell == 4)))
                            strArr[i] = strArr[i] + tmp[i];
                    	else
                    		strArr[i] = strArr[i] + tmp[i].trim();
                        // ��� ����������� ������������� ����: 08.01.2009  (��.��.����)                         
                        if (numStrCell == 2)
                            if (dat == i || dat_st == i)
                                if (strArr[i].length() > 5)
                                	strArr[i] = "'" + strArr[i].substring(0,2) + "." + strArr[i].substring(2,4) + ".20" + strArr[i].substring(4) + "'";
                    }                                    
                }
                }
            }
            bp.close();
        }
        catch (IOException ioe) 
        {
            error = true;
            System.out.println("Error read file: " + nameFileRead);
        }
        
        error = true;
        chekError();

    }
    
    /* ������ ������� � ������, � ����. ������� (������., ����������), �� ����������, c ��������, � ��������, �������� �����, 
     * ���������� ������� � ������ ����������� � ��, ����� ������� � ������������� �������� (��������), � ��������� ������� ����������, 
     * ����� ������� � ��������� ���� ������, � ������� ���������, � ������� ��������� ��� ���������� ������
     */
    private static void parse_pp(int dat, int dat_st, int st_ot, int st_n, int fare, int summ, int fio, String nameFileRead, int countColumns, 
    		int direct, int registr, int privilege, int num_doc, int num_dpr) {
        String[] strArr = "".split("");
        String strTmp = "";
        
        try
        {
	       Log log = new Log("${pathProject}/conf/BufferPool.properties");
	       BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
	       Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
	       Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
	       CachedRowSet crs = new CachedRowSet(log2);
	       String sql = prop.getString("sql.select");       

            boolean first = true;
            int numStrCell = 0;
            int dir_ind = 0;
            int reg_ind = 0;
            int vd_ind = 0;
            int kol = 0;
       	 	int view_doc = 0;
            String[] st = "".split("");
            String sta_otp = "";
            String sta_n = "";
            String famOne = "";
            int kolRow = 0;
            
            if (br != null)
            while ((strTmp = br.readLine()) != null)
            {
                numStrCell++;
                if (strTmp.indexOf("--") >= 0) kolRow++;
                if (kolRow >= 3) {
                if (strTmp.indexOf("--") > -1)
                {
                    numStrCell = 0;
                    if (strArr.length == countColumns + 1)
                    {
                    	strArr[0] = null;
                    	for (int i = 1; i < strArr.length; i++) {
                            if (strArr[i].length() == -1 || strArr[i].trim().equals("") || strArr[i].trim().equals("''"))
                                strArr[i] = null;
                            if (i == direct) {
                       	 		sql = prop.getString("sql.sel_direct");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 	    while (crs.next()) dir_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxDirect");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) dir_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_direct");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                       	 	crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(dir_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else log2.error(crs.getException());
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }
                            if ((i == registr) && (strArr[i] != null)) {
                           	 	reg_ind = 1;
                           	 	kol = i + 1;
                           	 	view_doc = i;
                            }

                            if ((i == (registr + 2)) && (strArr[i] != null)) {
                           	 	reg_ind = 2;
                           	 	kol = i + 1;
                           	 	view_doc = i;
                            }

                           	if ((i == view_doc) && (strArr[i] != null)) {
                           		if (strArr[i].indexOf(" ") > -1) {
                           			strArr[i] = strArr[i].substring(0,strArr[i].indexOf(" ")) + strArr[i].substring(strArr[i].lastIndexOf(" "));
                           		}
                       	 		sql = prop.getString("sql.sel_viewDoc");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) vd_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxViewDoc");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) vd_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_viewDoc");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(vd_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else log2.error(crs.getException());
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }

                            if (i == st_ot) {
                             	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                               			while (crs.next()) sta_otp = crs.getString(1);
                               		}
                               		else {
                            			sta_otp = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                      	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                    		log2.error(crs.getException());
                                    		break;
                                     	}
                               	 	}
                               	 }
                               	 else log2.error(crs.getException());
                            }
                            if (i == st_n) {
                               	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                             			while (crs.next()) sta_n = crs.getString(1);
                               		}
                               		else {
                               			sta_n = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                       	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                       		log2.error(crs.getException());
                                       		break;
                                       	}
                                 	}
                                }
                               	else log2.error(crs.getException());
                            }

                            if (i == privilege) {
                       	 		sql = prop.getString("sql.sel_privilege");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                       	 		crs.setSQL(sql);
                               	crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 			while (crs.next()) strArr[i] = crs.getString(1);
                           	 		}
                           	 		else {
                           	 			strArr[i] = strArr[i].trim();
                               	 		sql = prop.getString("sql.ins_privilege");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                   		crs.addParameter(strArr[i].trim());
                                   		if (!bp.updateQuery(crs) || crs.isError())
                                   		{
                                   			log2.error(crs.getException());
                                   			break;
                                   		}
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }
                            
                            if ((i == fio) && (strArr[i] != null)) {
                           		if (strArr[i].indexOf("=") > -1) 
                           			famOne = strArr[i].trim();
                           		else 
                           			famOne = strArr[i].substring(0,strArr[i].length() - 2).trim() + " " + strArr[i].substring(strArr[i].length() - 2);
                           	}
                            
                            if ((i == num_dpr) && (strArr[i] != null)) 
                            	strArr[i] = "'" + strArr[i].trim() + "'";
                    	}
                    	
                    	crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", " + strArr[privilege] + ", " + 
                    		sta_n + ", null, " + vd_ind + ", null, " + reg_ind + ", null, null, null, null, null, " + dir_ind + ", " + strArr[kol].trim() + ", " + 
                    		strArr[fare] + ", " + strArr[summ] + ", '" + famOne + "', null, " + strArr[dat_st] + ", " + strArr[num_dpr] + ", null," + doroga + ")");
                    	
                        if (!bp.updateQuery(crs) || crs.isError())
                        {
                        	log2.error(crs.getException());
                        	log2.debug("SQL:\n\t" + crs.getSQLAndParameters());
                        	break;
                        }
                    }
                    strArr = "".split("");
                	first = true;
                	continue;
                }
                if (first)
                {
                    first = false;
                    strArr = strTmp.split("!");
                }
                else
                {
                    String[] tmp = strTmp.split("!");
                    for (int i = 0; i < tmp.length; i++)
                    {
                        if (numStrCell == 2) {
                            if (st_ot == i || st_n == i)
                                strArr[i] = strArr[i] + ";";
                            if (view_doc == i)
                            	strArr[i] = strArr[i].trim() + " ";
                        }
                        strArr[i] = strArr[i] + tmp[i].trim();
                        if (numStrCell == 2)
                            if (dat == i || dat_st == i)
                                if (strArr[i].length() > 5)
                                	strArr[i] = "'" + strArr[i].substring(0,2) + "." + strArr[i].substring(2,4) + ".20" + strArr[i].substring(4) + "'";
                    }                                    
                }
                }
            }
            bp.close();
        }
        catch (IOException ioe) 
        {
            error = true;
            System.out.println("Error read file: " + nameFileRead);
        }
        
        error = true;
        chekError();
    }
    
    /* ������ ������� � ������, � ����. ������� (������., ����������), �� ����������, c ��������, � ��������, �������� �����, 
     * ���������� ������� � ������ ����������� � ��, ����� ������� � ������������� �������� (��������), � ��������� ������� ����������, 
     * ����� ������ (6000-� ��� 7000-�), ��� ������, ����� ������� � ������� ���������, � ���-��� �������, � ������� ��������� ��� ���������� ������
     */
    private static void parse_pr(int dat, int st_ot, int st_n, int fare, int summ, int fio, String nameFileRead, int countColumns, 
    		int direct, int registr, int num_p, int privilege, int num_doc, int kol, int num_dpr) {
        String[] strArr = "".split("");
        String strTmp = "";
        
        try
        {
	       Log log = new Log("${pathProject}/conf/BufferPool.properties");
	       BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
	       Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
	       Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
	       CachedRowSet crs = new CachedRowSet(log2);
	       String sql = prop.getString("sql.select");       

            boolean first = true;
            int numStrCell = 0;
            int dir_ind = 0;
            int reg_ind = 0;
            String[] st = "".split("");
            String sta_otp = "";
            String sta_n = "";
            String famOne = "";
            int kolRow = 0;
            
            if (br != null)
            while ((strTmp = br.readLine()) != null)
            {
                numStrCell++;
                if (strTmp.indexOf("--") >= 0) kolRow++;
                if (kolRow >= 3) {
                if (strTmp.indexOf("--") > -1)
                {
                    numStrCell = 0;
                    if (strArr.length == countColumns + 1)
                    {
                    	strArr[0] = null;
                    	for (int i = 1; i < strArr.length; i++) {
                            if (strArr[i].length() == -1 || strArr[i].trim().equals("") || strArr[i].trim().equals("''"))
                                strArr[i] = null;
                            if (i == direct) {
                       	 		sql = prop.getString("sql.sel_direct");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 	    while (crs.next()) dir_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxDirect");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) dir_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_direct");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                       	 	crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(dir_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else log2.error(crs.getException());
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }

                            if ((i == registr) && (strArr[i] != null))
                           	 	reg_ind = 3;

                            if ((i == (registr + 2)) && (strArr[i] != null))
                           	 	reg_ind = 2;

                            if (i == st_ot) {
                             	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                               			while (crs.next()) sta_otp = crs.getString(1);
                               		}
                               		else {
                            			sta_otp = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                      	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                    		log2.error(crs.getException());
                                    		break;
                                     	}
                               	 	}
                               	 }
                               	 else log2.error(crs.getException());
                            }
                            if (i == st_n) {
                               	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                             			while (crs.next()) sta_n = crs.getString(1);
                               		}
                               		else {
                               			sta_n = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                       	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                       		log2.error(crs.getException());
                                       		break;
                                       	}
                                 	}
                                }
                               	else log2.error(crs.getException());
                            }

                            if (i == privilege) {
                       	 		sql = prop.getString("sql.sel_privilege");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                       	 		crs.setSQL(sql);
                               	crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 			while (crs.next()) strArr[i] = crs.getString(1);
                           	 		}
                           	 		else {
                           	 			strArr[i] = strArr[i].trim();
                               	 		sql = prop.getString("sql.ins_privilege");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                   		crs.addParameter(strArr[i].trim());
                                   		if (!bp.updateQuery(crs) || crs.isError())
                                   		{
                                   			log2.error(crs.getException());
                                   			break;
                                   		}
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }
                            
                            if (i == fio)
                           		famOne = "'" + strArr[i].trim() + "'";
                            
                            if (i == num_dpr)
                            	strArr[i] = "'" + strArr[i].trim() + "'";
                    	}
                    	
                   		if (reg_ind == 0)
                           	crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", " + strArr[privilege] + ", " + 
                           		sta_n + ", null, null, " + num_p + ", null, null, null, null, null, null, " + dir_ind + ", " + strArr[kol].trim() + ", " + 
                           		strArr[fare] + ", " + strArr[summ] + ", " + famOne + ", null, null, " + strArr[num_dpr] + ", null," + doroga + ")");
                   		else
                   			crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", " + strArr[privilege] + ", " + 
                   				sta_n + ", null, null, " + num_p + ", " + reg_ind + ", null, null, null, null, null, " + dir_ind + ", " + strArr[kol].trim() + ", " + 
                 				strArr[fare] + ", " + strArr[summ] + ", " + famOne + ", null, null, " + strArr[num_dpr] + ", null," + doroga + ")");

                        if (!bp.updateQuery(crs) || crs.isError())
                        {
                        	log2.error(crs.getException());
                        	log2.debug("SQL:\n\t" + crs.getSQLAndParameters());
                        	break;
                        }
                    }
                    strArr = "".split("");
                	first = true;
                	continue;
                }
                if (first)
                {
                    first = false;
                    strArr = strTmp.split("!");
                }
                else
                {
                    String[] tmp = strTmp.split("!");
                    for (int i = 0; i < tmp.length; i++)
                    {
                        if (numStrCell == 2) {
                            if (st_ot == i || st_n == i)
                                strArr[i] = strArr[i] + ";";
                        }
                        strArr[i] = strArr[i] + tmp[i].trim();
                        if ((numStrCell == 2) && (dat == i) && (strArr[i].length() > 5))
                           	strArr[i] = "'" + strArr[i].substring(0,2) + "." + strArr[i].substring(2,4) + ".20" + strArr[i].substring(4) + "'";
                    }                                    
                }
                }
            }
            bp.close();
        }
        catch (IOException ioe) 
        {
            error = true;
            System.out.println("Error read file: " + nameFileRead);
        }
        
        error = true;
        chekError();

    }
    
	private static void readFile(boolean oneFio, String nameFileRead, String view_tr, String view_pass) {
        try {
        	n = "";
    		
            br = new BufferedReader(new FileReader(dir + doroga + "_" + year + "." + month + "\\" + nameFileRead)); // ����� � ����� ���������� ����� - nameFileRead
            
            if ((nameFileRead.indexOf("SP.") > -1) || (nameFileRead.indexOf("SP1.") > -1))
            	parse(7, 0, 10, 11, 12, 13, 5, oneFio, nameFileRead, 13, 2, 1, 3, 4, 0, 7000, 0, view_tr, view_pass, 8, 9, 6);
            else
            	parse(7, 8, 13, 14, 15, 0, 5, oneFio, nameFileRead, 15, 2, 1, 3, 4, 10, 6000, 11, view_tr, view_pass, 9, 12, 6);
        }
        catch (Exception e) {
            System.out.println("Error open file: " + nameFileRead);
            s += nameFileRead + "(" + n + "), ";
            error = true;
        }

        if (chekError()) return;
        
    }

	private static void readFile2(String nameFileRead) {
        try
        {
            br = new BufferedReader(new FileReader(dir + doroga + "_" + year + "." + month + "\\" + nameFileRead)); // ����� � ����� ���������� ����� - nameFileRead
            
            if (nameFileRead.indexOf("PP") > -1)
            	parse_pp(5, 6, 12, 13, 14, 15, 2, nameFileRead, 15, 1, 8, 4, 7, 3);
            else
            	parse_pr(5, 8, 9, 10, 11, 2, nameFileRead, 11, 1, 8, 7000, 4, 6, 7, 3);
        }
        catch (Exception e)
        {
            System.out.println("Error open file: " + nameFileRead);
            s += nameFileRead + ", ";
            error = true;
        }

        if (chekError()) return;
        
    }

    private static void writeTitle(String title, String fullNameFile) {
    	BufferedWriter bw;
    	
        try
        {            
            bw = new BufferedWriter(new FileWriter(fullNameFile));
            bw.write(title);
            bw.newLine();
            bw.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    private static void write(String fullNameFile, int num_p, int vp, int codOr, BufferPool bp, Log log, Log log2, Properties prop, int doroga) {
		String str = "";
        String[] st = "                ".split("");
		
		String view_doc = "";
		String registr = "";
		String code_org = "";
		String code_podr = "";
		String transp = "";
		String form_tr = "";
		String sta_o = "";
		String sta_n = "";
		
		int nstr = 0;

		BufferedWriter bw;
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       

        try
        {            
            bw = new BufferedWriter(new FileWriter(fullNameFile,true));
            sql = prop.getString("sql.sel_bundle"); 
            crs1.setSQL(sql);
            crs1.addParameter(year + "-" + month + "-31");
            crs1.addParameter(year + "-" + month + "-01");
            crs1.addParameter(num_p);
            crs1.addParameter(vp);
            crs1.addParameter(codOr);
            crs1.addParameter(doroga);
   	 		if (bp.executeQuery(crs1) && !crs1.isError())
   	 			if (crs1.size() > 0)
   	 				while (crs1.next()) {
   	 					for (int n = 1; n <= crs1.getColumnCount(); n++)
   	 						if (crs1.getString(n) == null) 
   	 							st[n] = "";
   	 						else
   	 							st[n] = crs1.getString(n);
   	 					if (num_p == 6000) {
   	 						sql = prop.getString("sql.sel_vDoc"); 
   	 						crs.setSQL(sql);
   	 						crs.addParameter(crs1.getInteger(5));
   	 						if (bp.executeQuery(crs) && !crs.isError())
   	 							if (crs.size() > 0) 
   	 								while (crs.next()) view_doc = crs.getString(1);
   	 							else
   	 								System.out.println("��� �������");
   	 						else
                       			log2.error(crs.getException());
   	 						sql = prop.getString("sql.sel_reg"); 
   	 						crs.setSQL(sql);
   	 						crs.addParameter(crs1.getInteger(6));
   	 						if (bp.executeQuery(crs) && !crs.isError())
   	 							if (crs.size() > 0)
   	   	 							while (crs.next()) registr = crs.getString(1);
   	 							else
   	 								System.out.println("��� �������");
   	 						else
   	 							log2.error(crs.getException());
   	 					}
	 					sql = prop.getString("sql.sel_codOrg"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(codOr);
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_org = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_codPodr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(7));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_podr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namTransp"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(8));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) transp = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namFormTr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(9));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) form_tr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(3));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_o = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(4));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_n = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
   	   	 				if (num_p == 6000)
   	   	 					str = "<tr valign=\"top\" align=\"center\" ><td align=\"left\" >" + transp + "</td><td>" + form_tr + "</td><td>" + 
   	   	 					code_org + "</td><td>" + code_podr + "</td><td align=\"left\" >" + st[13] + "<br>" + st[12] + "</td><td class=\"number\" >" + st[14] + "</td><td>" + 
   	   	 					st[1] + "</td><td>" + st[15] + "</td><td>" + st[2] + "</td><td>" + registr + "</td><td align=\"left\" >" + view_doc + 
   	   	 					"</td><td>" + st[10] + "</td><td align=\"left\" >" + sta_o + "</td><td>" + st[3] + "</td><td align=\"left\" >" + sta_n + 
   	   	 					"</td><td>" + st[4] + "</td><td class=\"number\">" + st[11] + "</td></tr>";
   	   	 				else
   	   	 					str = "<tr valign=\"top\" align=\"center\" ><td align=\"left\" >" + transp + "</td><td>" + form_tr + "</td><td>" + 
   	   	 					code_org + "</td><td>" + code_podr + "</td><td align=\"left\" >" + st[13] + "<br>" + st[12] + "</td><td class=\"number\">" + st[14] + "</td><td>" + 
   	   	 					st[1] + "</td><td>" + st[2] + "</td><td>" + st[10] + "</td><td align=\"left\" >" + sta_o + "</td><td>" + st[3] + 
   	   	 					"</td><td align=\"left\" >" + sta_n + "</td><td>" + st[4] + "</td><td class=\"number\" >" + st[11] + 
   	   	 					"</td><td class=\"number\" >" + st[15] + "</td></tr>";
   	   	 				bw.write(str);
   	 					bw.newLine();
   	 				}
   	 			else
   	 				System.out.println("��� �������");
   	 		else
   	 			log2.error(crs1.getException());
 			if ((st[12] == null) || (st[12] == ""))
	   	 		nstr = crs1.size() + 2;
 			else
	   	 		nstr = crs1.size()*2 + 2;
	 		if (num_p == 6000)
	 			bw.write("<tr><td colspan=\"16\" align=\"right\" ><b>�����</b></td><td colspan=\"1\" >=����(Q3:Q" + nstr + ")</td></tr>");
	 		else
	 			bw.write("<tr><td colspan=\"13\" align=\"right\" ><b>�����</b></td><td colspan=\"2\" >=����(N3:O" + nstr + ")</td></tr>");
			bw.write("</table></body></html>");
            bw.close();
        }
        catch (Exception e)
        {
            error = true;
            System.out.println(e);
        }
        chekError();
    }
    
    private static void writeViewTr(String fullNameFile, int vt, int codOr, BufferPool bp, Log log, Log log2, Properties prop, int doroga) {
		String str = "";
        String[] st = "                ".split("");
		
		String view_doc = "";
		String registr = "";
		String code_org = "";
		String code_podr = "";
		String transp = "";
		String form_tr = "";
		String sta_o = "";
		String sta_n = "";
		
		int nstr = 0;

		BufferedWriter bw;
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       

        try
        {            
            bw = new BufferedWriter(new FileWriter(fullNameFile,true));
            sql = prop.getString("sql.sel_bundleVTr"); 
            crs1.setSQL(sql);
            crs1.addParameter(year + "-" + month + "-31");
            crs1.addParameter(year + "-" + month + "-01");
            crs1.addParameter(vt);
            crs1.addParameter(codOr);
            crs1.addParameter(doroga);
   	 		if (bp.executeQuery(crs1) && !crs1.isError())
   	 			if (crs1.size() > 0)
   	 				while (crs1.next()) {
   	 					for (int n = 1; n <= crs1.getColumnCount(); n++)
   	 						if (crs1.getString(n) == null) 
   	 							st[n] = "";
   	 						else
   	 							st[n] = crs1.getString(n);
 						sql = prop.getString("sql.sel_vDoc"); 
 						crs.setSQL(sql);
 						crs.addParameter(crs1.getInteger(5));
 						if (bp.executeQuery(crs) && !crs.isError())
 							if (crs.size() > 0) 
 								while (crs.next()) view_doc = crs.getString(1);
 							else
 								System.out.println("��� �������");
 						else
                   			log2.error(crs.getException());
 						sql = prop.getString("sql.sel_reg"); 
 						crs.setSQL(sql);
 						crs.addParameter(crs1.getInteger(6));
 						if (bp.executeQuery(crs) && !crs.isError())
 							if (crs.size() > 0)
   	 							while (crs.next()) registr = crs.getString(1);
 							else
 								System.out.println("��� �������");
 						else
 							log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_codOrg"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(codOr);
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_org = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_codPodr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(7));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_podr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namTransp"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(8));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) transp = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namFormTr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(9));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) form_tr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(3));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_o = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(4));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_n = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	   	 				str = "<tr valign=\"top\" align=\"center\" ><td align=\"left\" >" + transp + "</td><td>" + form_tr + "</td><td>" + registr + "</td><td>" + 
	   	 				st[2] + "</td><td class=\"number\" >" + st[14] + "</td><td align=\"left\" >" + view_doc + "</td><td>" + st[1] + "</td><td>76</td><td>2010/03</td><td>" + 
	   	 				code_org + "</td><td>" + code_org + "</td><td>" + code_podr + "</td><td>" + st[4] + "</td><td align=\"left\" >" + sta_n + "</td><td>" + 
	   	 				st[3] + "</td><td align=\"left\" >" + sta_o + "</td><td align=\"left\" >" + st[13] + "<br>" + st[12] + "</td><td>" + 
	   	 				st[10] + "</td><td class=\"number\">" + st[16] + "</td><td class=\"number\" >" + st[11] + "</td></tr>";
   	   	 				bw.write(str);
   	 					bw.newLine();
   	 				}
   	 			else
   	 				System.out.println("��� �������");
   	 		else
   	 			log2.error(crs1.getException());
 			if ((st[12] == null) || (st[12] == ""))
	   	 		nstr = crs1.size() + 2;
 			else
	   	 		nstr = crs1.size()*2 + 2;
	 		bw.write("<tr><td colspan=\"18\" align=\"right\" ><b>�����</b></td><td colspan=\"2\" >=����(S3:T" + nstr + ")</td></tr>");
			bw.write("</table></body></html>");
            bw.close();
        }
        catch (Exception e)
        {
            error = true;
            System.out.println(e);
        }
        chekError();
    }
    
    private static void itogo(BufferPool bp, Log log, Log log2, Properties prop, int kolOrg, int kolVPass) {
    	String fullNameFile = dir + "\\" + doroga + "_" + year + "." + month + "\\Itogo.xls";
    	String str = "";
    	int nstr = 1;
    	double summ = 0.0;
    	int i = 0;
		
    	BufferedWriter bw;
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        CachedRowSet crs3 = new CachedRowSet(log2);
        String sql;       

        try
        {            
            bw = new BufferedWriter(new FileWriter(fullNameFile));
			bw.write("<html>\n" + 
					"<style>.number {mso-number-format:\"0\";}</style><body>\n" + 
					"<table border=\"1\">\n" + 
					"<tr><th width=90 >��� �����������</th>" +
					"<th width=125 >��������� �����������, ������������� ��� ���</th>" + 
					"<th width=177 >����, ����������� �� ��������� ���������� � ����������� ��� ���</th>" + 
					"<th width=90 >���������� ��� ���</th>" + 
					"<th width=76 >��������� ��� ���</th>" + 
					"<th width=60 >���� ����</th>" + 
					"<th width=143 >����, �������� ������� ������� � ���������� �.�. ������</th>" + 
					"<th width=177 >����, ����������� �� ��������� � ������������ ����������� ��� ���</th>" + 
					"<th width=90 >���������� ��� ���</th>" + 
					"<th width=76 >��������� ��� ���</th>" + 
					"<th width=177 >����, ����������� �� ��������� ���������� � ����������� ��� ���</th>" + 
					"<th width=177 >����, ����������� �� ��������� � ������������ ����������� ��� ���</th>" + 
//					"<th width=125 >��������� �����������, ������������� ��� ���</th>" + 
//					"<th width=143 >����, �������� ������� �������� ������������ ��� � ������� � ���������� �.�. ������</th>" + 
					"<th width=65 >�����</th></tr>");
   	 		sql = prop.getString("sql.orgMonth"); 
   	 		crs3.setSQL(sql);
   	 		crs3.addParameter(year + "-" + month + "-01");
   	 		crs3.addParameter(doroga);
   	 		crs3.addParameter(year + "-" + month + "-31");
    	    if (bp.executeQuery(crs3) && !crs3.isError())
   	    		while (crs3.next()) {
   	    			kolOrg = crs3.size();
   	    			i = crs3.getInteger(1);
				sql = prop.getString("sql.sel_codOrg"); 
                crs.setSQL(sql);
                crs.addParameter(i);
        		if (bp.executeQuery(crs) && !crs.isError())
       				if (crs.next()) {
                    	str = "<tr><td>" + crs.getString(1) + "</td>";
                    	for (int j = 1; j <= kolVPass; j++) {
                    		sql = prop.getString("sql.sel_sumVPass"); 
                    		crs1.setSQL(sql);
                    		crs1.addParameter(year + "-" + month + "-01");
                    		crs1.addParameter(j);
                    		crs1.addParameter(i);
                    		crs1.addParameter(doroga);
                    		crs1.addParameter(year + "-" + month + "-31");
                    		if (bp.executeQuery(crs1) && !crs1.isError())
                    			if (crs1.size() > 0) {
                    				if (crs1.next())
                    					summ = crs1.getDouble(1,0);
                    			}
                    			else
                    				System.out.println("��� ����� ��� ���������� ���� ���������");
                    		else
                    			log2.error(crs1.getException());
                    		str = str + "<td class=\"number\" align=\"center\" >" + summ + "</td>";
                    		summ = 0.0;
                    	}
                    	nstr++;
                    	str = str + "<td>=����(B" + nstr + ":N" + nstr + ")</td></tr>";
                    	bw.write(str);
       				}
       				else
        				System.out.println("��� �����������");
        		else
        			log2.error(crs.getException());
            }
			nstr = kolOrg + 1;
			bw.write("<tr><td align=\"right\" ><b>�����</b></td><td>=����(B2:B" + nstr + ")</td><td>=����(C2:C" + nstr + ")</td><td>=����(D2:D" + nstr + 
					")</td><td>=����(E2:E" + nstr + ")</td><td>=����(F2:F" + nstr + ")</td><td>=����(G2:G" + nstr + ")</td><td>=����(H2:H" + nstr + 
					")</td><td>=����(I2:I" + nstr + ")</td><td>=����(J2:J" + nstr + ")</td><td>=����(K2:K" + nstr + ")</td><td>=����(L2:L" + nstr + 
					")</td><td>=����(M2:M" + nstr + ")</td><td>=����(N2:N" + nstr + ")</td><td>=����(O2:O" + nstr + ")</td></tr>");
			bw.write("</table></body></html>");
            bw.close();
        }
        catch (Exception e)
        {
            error = true;
            System.out.println(e);
        }
        chekError();
    }

    public static void createFile() {
		String subDir = "";
		String fullNameFile = "";
		int kolOrg = 0;
		int kolVPass = 0;
		int j = 0;
		String title6 = "<html>\n" + 
		   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
		   "       <table border=\"1\" >\n" + 
		   "<tr><th rowspan=\"2\" width=120 >������������ �����������</th>" +
		   "<th rowspan=\"2\" width=60 >����� ������ ��.</th>" + 
		   "<th rowspan=\"2\" width=108 >��� �����������</th>" +
		   "<th rowspan=\"2\" width=112 >��� �������������</th>" +
		   "<th rowspan=\"2\" width=160 >�������, �������� ���������</th>" + 
		   "<th rowspan=\"2\" width=108 >����� ������. ����������</th>" + 
		   "<th rowspan=\"2\" width=94 >���� ���������� ���</th>" + 
		   "<th rowspan=\"2\" width=94 >���� ������ �������� ����������</th>" + 
		   "<th rowspan=\"2\" width=71 >����� ������. ������</th>" + 
		   "<th rowspan=\"2\" width=92 >������ ����������</th>" + 
		   "<th rowspan=\"2\" width=80 >��� ���������</th>" + 
		   "<th rowspan=\"2\" width=85 >���������� ����������</th>" + 
		   "<th colspan=\"4\" >������� (����)</th>" + 
		   "<th rowspan=\"2\" >���������</th>" +
		   "</tr><th colspan=\"2\">�����������</th><th colspan=\"2\">����������</th></tr>";
        String title7 = "<html>\n" + 
		   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
		   "       <table border=\"1\" >\n" + 
		   "<tr><th rowspan=\"2\" width=120 >������������ �����������</th>" +
		   "<th rowspan=\"2\" width=60 >����� ������ ��.</th>" + 
		   "<th rowspan=\"2\" width=108 >��� �����������</th>" +
		   "<th rowspan=\"2\" width=112 >��� �������������</th>" +
		   "<th rowspan=\"2\" width=160 >�������, �������� ���������, �������, �������� ���������, �� ���� ���������</th>" + 
		   "<th rowspan=\"2\" width=115 >����� ������. ����������</th>" + 
		   "<th rowspan=\"2\" width=94 >���� ���������� ���</th>" + 
		   "<th rowspan=\"2\" width=71 >����� ������. ������</th>" + 
		   "<th rowspan=\"2\" width=85 >���������� ������� ��� ��������</th>" +		   
		   "<th colspan=\"4\">������� (����)</th>" + 
		   "<th rowspan=\"2\">���������</th>" +
		   "<th rowspan=\"2\" width=111 >������� (����� ������������� ����������)</th>" +
		   "</tr><th colspan=\"2\">�����������</th><th colspan=\"2\">����������</th></tr>";
		
        Log log = new Log("${pathProject}/conf/BufferPool.properties");
        BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
        Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
        Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       

   	 	File f;
   // ���-�� ����������� (��� �������� � �����)
        sql = prop.getString("sql.sel_countOrg"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolOrg = crs.getInteger(1);
   	 	}
   	 	else
   	 		log2.error(crs.getException());
        
   // ���-�� ����� ���������� (��� �������� � �����)
        sql = prop.getString("sql.sel_countVPass"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolVPass = crs.getInteger(1);
   	 		else
   	 			System.out.println("��� �������");
   	 	else
   	 		log2.error(crs.getException());
        
/*	 	for (int i = 1; i <= kolVPass; i++) {
        	// ������� ����� � �������� ����������: viewPass
            sql = prop.getString("sql.sel_vPass"); 
            crs.setSQL(sql);
            crs.addParameter(i);
       	 	if (bp.executeQuery(crs) && !crs.isError())
 			if (crs.next()) {
       	 				subDir = crs.getString(1);
       	 				f = new File(dir + doroga + "_" + year+ "." + month + "\\" + subDir);
       	 				f.mkdirs();
 			}
       	 	else
       	 		log2.error(crs.getException());

       	 	for (int k = 6000; k <= 7000; k += 1000) {
       	 		sql = prop.getString("sql.spisokOrg"); 
       	 		crs1.setSQL(sql);
       	 		crs1.addParameter(year + "-" + month + "-01");
       	 		crs1.addParameter(year + "-" + month + "-31");
       	 		crs1.addParameter(k);
       	 		crs1.addParameter(i);
       	 		crs1.addParameter(doroga);
        	    if (bp.executeQuery(crs1) && !crs1.isError())
        	    	if (crs1.size() > 0)
        	    		while (crs1.next()) {
            	        	j = crs1.getInteger(1);
            	        		// �������� ���� � �����: codeOrg_numP.xls
        	    			sql = prop.getString("sql.sel_codOrg"); 
        	    			crs.setSQL(sql);
        	    			crs.addParameter(j);
        	        	    if (bp.executeQuery(crs) && !crs.isError())
        	        	    	if (crs.size() > 0) {
        	        	    		if (crs.next()) 
        	        	    			fullNameFile = dir + doroga + "_" + year + "." + month + "\\" + subDir + "\\" + crs.getString(1) + "_" + k + ".xls";
        	        	    	}
        	        	    	else
        	        	    		System.out.println("��� �������");
        	        	    else
        	        	    	System.out.println(crs.getException());
        	    			if (k == 6000)
        	    				writeTitle(title6, fullNameFile);
        	    			else
        	    				writeTitle(title7, fullNameFile);
        	    			write(fullNameFile, k, i, j, bp, log, log2, prop, doroga);
        	    		}
        	    	else
        	    		System.out.println("��� �������");
        	    else
        	    	log2.error(crs1.getException());
       	 	}
        }
  */  
        // �������� �������
        itogo(bp, log, log2, prop, kolOrg, kolVPass);
	}

    public static void createFileViewTr() {
		String subDir = "";
		String fullNameFile = "";
		int kolVTransp = 0;
		int j = 0;
        String title = "<html>\n" + 
		   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
		   "       <table border=\"1\" >\n" + 
		   "<tr><th rowspan=\"2\" width=120 >������������ �������� (��������)</th>" +
		   "<th rowspan=\"2\" width=60 >����� ������������� ����������</th>" + 
		   "<th rowspan=\"2\" width=92 >������ ���������� ���������� ���������</th>" + 
		   "<th rowspan=\"2\" width=71 >����� ������������ ������ ��� ��������</th>" + 
		   "<th rowspan=\"2\" width=108 >N ������������� ����������</th>" + 
		   "<th rowspan=\"2\" width=80 >��� ���������</th>" + 
		   "<th rowspan=\"2\" width=94 >���� ���������� ���������� ���������</th>" + 
		   "<th rowspan=\"2\" width=108 >������ ����������</th>" +
		   "<th rowspan=\"2\" width=80 >�����</th>" + 
		   "<th rowspan=\"2\" width=108 >����� �����</th>" +
		   "<th rowspan=\"2\" width=108 >��� �����������, � ����� �������� ������� ��������</th>" +
		   "<th rowspan=\"2\" width=112 >��� �������������, ��������� ������������ ����������</th>" +
		   "<th colspan=\"4\" >������� (����)</th>" + 
		   "<th rowspan=\"2\" width=160 >�������, ��������</th>" + 
		   "<th rowspan=\"2\" width=85 >���-�� ����������</th>" + 
		   "<th rowspan=\"2\" width=111 >�������, ���.</th>" +
		   "<th rowspan=\"2\">��������� �������, ���������� ����������,���.</th>" +
		   "</tr><th colspan=\"2\">�����������</th><th colspan=\"2\">����������</th></tr>";
		
        Log log = new Log("${pathProject}/conf/BufferPool.properties");
        BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
        Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
        Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;     

   	 	File f;
   // ���-�� ����� ��������� (��� �������� � �����)
        sql = prop.getString("sql.sel_countVTransp"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolVTransp = crs.getInteger(1);
   	 		else
   	 			System.out.println("��� �������");
   	 	else
   	 		log2.error(crs.getException());
        
	 	for (int i = 2; i <= kolVTransp; i++) {
        	// ������� ����� � �������� ����������: viewTr
            sql = prop.getString("sql.sel_vTr"); 
            crs.setSQL(sql);
            crs.addParameter(i);
       	 	if (bp.executeQuery(crs) && !crs.isError())
 			if (crs.next()) {
       	 				subDir = crs.getString(1);
       	 				f = new File(dir + doroga + "_" + year+ "." + month + "\\" + subDir);
       	 				f.mkdirs();
 			}
       	 	else
       	 		log2.error(crs.getException());

   	 		sql = prop.getString("sql.spisokOrgVTr"); 
   	 		crs1.setSQL(sql);
   	 		crs1.addParameter(year + "-" + month + "-31");
   	 		crs1.addParameter(year + "-" + month + "-01");
   	 		crs1.addParameter(i);
   	 		crs1.addParameter(doroga);
      	    if (bp.executeQuery(crs1) && !crs1.isError())
       	    	if (crs1.size() > 0)
       	    		while (crs1.next()) {
           	        	j = crs1.getInteger(1);
           	        		// �������� ���� � �����: codeOrg_numP.xls
       	    			sql = prop.getString("sql.sel_codOrg"); 
       	    			crs.setSQL(sql);
       	    			crs.addParameter(j);
       	        	    if (bp.executeQuery(crs) && !crs.isError())
       	        	    	if (crs.size() > 0) {
       	        	    		if (crs.next()) 
       	        	    			fullNameFile = dir + doroga + "_" + year + "." + month + "\\" + subDir + "\\" + crs.getString(1) + ".xls";
       	        	    	}
       	        	    	else
       	        	    		System.out.println("��� �������");
       	        	    else
       	        	    	System.out.println(crs.getException());
   	    				writeTitle(title, fullNameFile);
       	    			writeViewTr(fullNameFile, i, j, bp, log, log2, prop, doroga);
       	    		}
       	    	else
      	    		System.out.println("��� �������");
       	    else
       	    	log2.error(crs1.getException());
        }
	}

    String compute(String bslp, String bslsp, String dslp, String dslsp, String fslp, String fslsp, String ilnp, String ilnsp, String nlnp, String nlnsp, String plnp, String plnsp, String pp, String pprzd, String pr, String prrzd, String rlnp, String rlnsp, String rrup, String rrusp, String rsnp, String rsnsp, String glnp, String glnsp, String romp, String romsp, String bslp1, String bslsp1, String dslp1, String dslsp1, String fslp1, String fslsp1, String ilnp1, String ilnsp1, String nlnp1, String nlnsp1, String plnp1, String plnsp1, String pp1, String pprzd1, String pr1, String prrzd1, String rlnp1, String rlnsp1, String rrup1, String rrusp1, String rsnp1, String rsnsp1, String glnp1, String glnsp1, String romp1, String romsp1, String pass, String transp) {
        int timeB = 0;
        int timeEnd = 0;
        String result = "";
        
		Log log = new Log("${pathProject}/conf/BufferPool.properties");
		BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
		Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
		Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
		CachedRowSet crs = new CachedRowSet(log2); 
		String sql;       

        sql = prop.getString("sql.time"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		if (crs.size() > 0)
   	 			if (crs.next()) timeB = crs.getInteger(1);
   	 	}
   	 	else
   	 		log2.error(crs.getException());

		bp.close();
	   	 	
	// readFile(boolean oneFio, String nameFileRead, String view_tr, String view_pass, int numRow)
		if (bslp == "BSLP")
			readFile(true, "BSLP.txt", "�� ������� ����������� OAO P��", "���� ����");
		if (bslsp == "BSLSP")
			readFile(true, "BSLSP.txt", "�� ������� ����������� OAO P��", "���� ����");
		if (dslp == "DSLP")
			readFile(true, "DSLP.txt", "�� ��������� � OAO P��", "���� ����");
		if (dslsp == "DSLSP")
			readFile(true, "DSLSP.txt", "�� ��������� � OAO P��", "���� ����");
		if (fslp == "FSLP")
			readFile(true, "FSLP.txt", "", "��������� �����������, ������������� ��� ���");
		if (fslsp == "FSLSP")
			readFile(true, "FSLSP.txt", "", "��������� �����������, ������������� ��� ���");
		if (ilnp == "ILNP")
			readFile(false, "ILNP.txt", "�� ������ �����������", "����, ����������� �� ��������� ���������� � ����������� ��� ���");
		if (ilnsp == "ILNSP")
			readFile(false, "ILNSP.txt", "�� ������ �����������", "����, ����������� �� ��������� ���������� � ����������� ��� ���");
		if (glnp == "GLNP")
			readFile(false, "GLNP.txt", "�� ������ �����������", "����, ����������� �� ��������� � ������������ ����������� ��� ���");
		if (glnsp == "GLNSP")
			readFile(false, "GLNSP.txt", "�� ������ �����������", "����, ����������� �� ��������� � ������������ ����������� ��� ���");
		if (nlnp == "NLNP")
			readFile(true, "NLNP.txt", "�� ������ �����������", "����, �������� ������� ������� � ���������� �.�. ������");
		if (nlnsp == "NLNSP")
			readFile(true, "NLNSP.txt", "�� ������ �����������", "����, �������� ������� ������� � ���������� �.�. ������");
		if (plnp == "PLNP")
			readFile(true, "PLNP.txt", "", "����������");
		if (plnsp == "PLNSP")
			readFile(true, "PLNSP.txt", "", "����������"); 
		if (rlnp == "RLNP")
			readFile(true, "RLNP.txt", "�� ������ �����������", "��������� ��� ���");
		if (rlnsp == "RLNSP")
			readFile(true, "RLNSP.txt", "�� ������ �����������", "��������� ��� ���");
		if (romp == "ROMP")
			readFile(true, "ROMP.txt", "��������� �� �������� � ���������� ������", "��������� ��� ���");
		if (romsp == "ROMSP")
			readFile(true, "ROMSP.txt", "��������� �� �������� � ���������� ������", "��������� ��� ���");
		if (rrup == "RRUP")
			readFile(true, "RRUP.txt", "�� ���� �� ������/�����", "��������� ��� ���");
		if (rrusp == "RRUSP")
			readFile(true, "RRUSP.txt", "�� ���� �� ������/�����", "��������� ��� ���");
		if (rsnp == "RSNP")
			readFile(true, "RSNP.txt", "���������", "��������� ��� ���");
		if (rsnsp == "RSNSP")
			readFile(true, "RSNSP.txt", "���������", "��������� ��� ���");

		if (bslp1 == "BSLP1")
			readFile(true, "B�LP1.txt", "�� ������� ����������� OAO ���", "���� ����");
		if (bslsp1 == "BSLSP1")
			readFile(true, "B�LSP1.txt", "�� ������� ����������� OAO ���", "���� ����");
		if (dslp1 == "DSLP1")
			readFile(true, "D�LP1.txt", "�� ��������� � OAO ���", "���� ����");
		if (dslsp1 == "DSLSP1")
			readFile(true, "D�LSP1.txt", "�� ��������� � OAO ���", "���� ����");
		if (fslp1 == "FSLP1")
			readFile(true, "F�LP1.txt", "", "��������� �����������, ������������� ��� ���");
		if (fslsp1 == "FSLSP1")
			readFile(true, "F�LSP1.txt", "", "��������� �����������, ������������� ��� ���");
		if (ilnp1 == "ILNP1")
			readFile(false, "ILNP1.txt", "�� ������ �����������", "����, ����������� �� ��������� ���������� � ����������� ��� ���");
		if (ilnsp1 == "ILNSP1")
			readFile(false, "ILNSP1.txt", "�� ������ �����������", "����, ����������� �� ��������� ���������� � ����������� ��� ���");
		if (glnp1 == "GLNP1")
			readFile(false, "GLNP1.txt", "�� ������ �����������", "����, ����������� �� ��������� � ������������ ����������� ��� ���");
		if (glnsp1 == "GLNSP1")
			readFile(false, "GLNSP1.txt", "�� ������ �����������", "����, ����������� �� ��������� � ������������ ����������� ��� ���");
		if (nlnp1 == "NLNP1")
			readFile(true, "NLNP1.txt", "�� ������ �����������", "����, �������� ������� �������� ������������ ��� � ������� � ���������� �.�. ������");
		if (nlnsp1 == "NLNSP1")
			readFile(true, "NLNSP1.txt", "�� ������ �����������", "����, �������� ������� �������� ������������ ��� � ������� � ���������� �.�. ������");
		if (plnp1 == "PLNP1")
			readFile(true, "PLNP1.txt", "�� ������ �����������", "���������� OAO ���");
		if (plnsp1 == "PLNSP1")
			readFile(true, "PLNSP1.txt", "�� ������ �����������", "���������� OAO ���"); 
		if (rlnp1 == "RLNP1")
			readFile(true, "RLNP1.txt", "�� ������ �����������", "��������� ��� ���");
		if (rlnsp1 == "RLNSP1")
			readFile(true, "RLNSP1.txt", "�� ������ �����������", "��������� ��� ���");
		if (romp1 == "ROMP1")
			readFile(true, "ROMP1.txt", "��������� �� �������� � ���������� ������", "��������� ��� ���");
		if (romsp1 == "ROMSP1")
			readFile(true, "ROMSP1.txt", "��������� �� �������� � ���������� ������", "��������� ��� ���");
		if (rrup1 == "RRUP1")
			readFile(true, "RRUP1.txt", "�� ���� �� ������/�����", "��������� ��� ���");
		if (rrusp1 == "RRUSP1")
			readFile(true, "RRUSP1.txt", "�� ���� �� ������/�����", "��������� ��� ���");
		if (rsnp1 == "RSNP1")
			readFile(true, "RSNP1.txt", "���������", "��������� ��� ���");
		if (rsnsp1 == "RSNSP1")
			readFile(true, "RSNSP1.txt", "���������", "��������� ��� ���");

	// readFile2(String nameFileRead, int numRow)
		if (pp == "PP")
			readFile2("PP.txt");
		if (pprzd == "PPRZD")
			readFile2("PPRZD.txt");
		if (pr == "PR")
			readFile2("PR.txt");
		if (prrzd == "PRRZD")
			readFile2("PRRZD.txt");

   	 	System.out.println(s);
   	 	result += s;
   	 	if ((s == "") && (pass == "yes"))
   	 		createFile();
   	 	if ((s == "") && (transp == "yes"))
   	 		createFileViewTr();
   	 	
		log = new Log("${pathProject}/conf/BufferPool.properties");
		bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
		log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
		prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
		crs = new CachedRowSet(log2); 

		sql = prop.getString("sql.time"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		if (crs.size() > 0)
   	 			if (crs.next()) timeEnd = crs.getInteger(1);
   	 	}
   	 	else
   	 		log2.error(crs.getException());
   	 	bp.close();

   	 	System.out.println("����� ������ ���������: " + (timeEnd-timeB)/60 + " ����� " + ((timeEnd-timeB)-((timeEnd-timeB)/60)*60) + " �����");
   	 	result += "����� ������ ���������: " + (timeEnd-timeB)/60 + " ����� " + ((timeEnd-timeB)-((timeEnd-timeB)/60)*60) + " �����";
   	 	return result;
	}
}