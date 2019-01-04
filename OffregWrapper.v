module PixelOffreg( // @[:@3.2]
  output [31:0] io_offset_x_0, // @[:@6.4]
  output [31:0] io_offset_x_1, // @[:@6.4]
  output [31:0] io_offset_x_2, // @[:@6.4]
  output [31:0] io_offset_y_6, // @[:@6.4]
  output        io_vld_5 // @[:@6.4]
);
  wire [52:0] _T_171; // @[offreg.scala 17:15:@40.4]
  wire [61:0] _GEN_0; // @[offreg.scala 11:15:@42.4]
  wire [62:0] _T_173; // @[offreg.scala 11:15:@42.4]
  wire [61:0] _T_174; // @[offreg.scala 11:15:@43.4]
  wire [61:0] _T_175; // @[offreg.scala 11:15:@44.4]
  wire [52:0] _T_179; // @[offreg.scala 17:15:@48.4]
  wire [61:0] _GEN_2; // @[offreg.scala 11:15:@50.4]
  wire [62:0] _T_181; // @[offreg.scala 11:15:@50.4]
  wire [61:0] _T_182; // @[offreg.scala 11:15:@51.4]
  wire [61:0] _T_183; // @[offreg.scala 11:15:@52.4]
  wire [53:0] _T_187; // @[offreg.scala 17:15:@56.4]
  wire [61:0] _GEN_4; // @[offreg.scala 11:15:@58.4]
  wire [62:0] _T_189; // @[offreg.scala 11:15:@58.4]
  wire [61:0] _T_190; // @[offreg.scala 11:15:@59.4]
  wire [61:0] _T_191; // @[offreg.scala 11:15:@60.4]
  wire [54:0] _T_211; // @[offreg.scala 17:15:@80.4]
  wire [54:0] _T_212; // @[offreg.scala 18:15:@81.4]
  wire [61:0] _GEN_10; // @[offreg.scala 11:15:@82.4]
  wire [62:0] _T_213; // @[offreg.scala 11:15:@82.4]
  wire [61:0] _T_214; // @[offreg.scala 11:15:@83.4]
  wire [61:0] _T_215; // @[offreg.scala 11:15:@84.4]
  wire [61:0] _GEN_11; // @[offreg.scala 12:15:@85.4]
  wire [62:0] _T_216; // @[offreg.scala 12:15:@85.4]
  wire [61:0] _T_217; // @[offreg.scala 12:15:@86.4]
  wire [61:0] _T_218; // @[offreg.scala 12:15:@87.4]
  wire [54:0] _T_220; // @[offreg.scala 18:15:@89.4]
  wire [61:0] _GEN_13; // @[offreg.scala 12:15:@93.4]
  wire [62:0] _T_224; // @[offreg.scala 12:15:@93.4]
  wire [61:0] _T_225; // @[offreg.scala 12:15:@94.4]
  wire [61:0] _T_226; // @[offreg.scala 12:15:@95.4]
  wire  _T_316; // @[offreg.scala 22:12:@215.4]
  wire  _T_317; // @[offreg.scala 22:26:@216.4]
  assign _T_171 = $signed(32'sh0) * $signed(32'sh0); // @[offreg.scala 17:15:@40.4]
  assign _GEN_0 = {{9{_T_171[52]}},_T_171}; // @[offreg.scala 11:15:@42.4]
  assign _T_173 = $signed(62'sh64000000000000) + $signed(_GEN_0); // @[offreg.scala 11:15:@42.4]
  assign _T_174 = $signed(62'sh64000000000000) + $signed(_GEN_0); // @[offreg.scala 11:15:@43.4]
  assign _T_175 = $signed(_T_174); // @[offreg.scala 11:15:@44.4]
  assign _T_179 = $signed(32'sh0) * $signed(-32'sh100000); // @[offreg.scala 17:15:@48.4]
  assign _GEN_2 = {{9{_T_179[52]}},_T_179}; // @[offreg.scala 11:15:@50.4]
  assign _T_181 = $signed(62'sh64000000000000) + $signed(_GEN_2); // @[offreg.scala 11:15:@50.4]
  assign _T_182 = $signed(62'sh64000000000000) + $signed(_GEN_2); // @[offreg.scala 11:15:@51.4]
  assign _T_183 = $signed(_T_182); // @[offreg.scala 11:15:@52.4]
  assign _T_187 = $signed(32'sh0) * $signed(-32'sh200000); // @[offreg.scala 17:15:@56.4]
  assign _GEN_4 = {{8{_T_187[53]}},_T_187}; // @[offreg.scala 11:15:@58.4]
  assign _T_189 = $signed(62'sh64000000000000) + $signed(_GEN_4); // @[offreg.scala 11:15:@58.4]
  assign _T_190 = $signed(62'sh64000000000000) + $signed(_GEN_4); // @[offreg.scala 11:15:@59.4]
  assign _T_191 = $signed(_T_190); // @[offreg.scala 11:15:@60.4]
  assign _T_211 = $signed(32'sh0) * $signed(-32'sh300000); // @[offreg.scala 17:15:@80.4]
  assign _T_212 = $signed(-32'sh40000000) * $signed(-32'sh300000); // @[offreg.scala 18:15:@81.4]
  assign _GEN_10 = {{7{_T_211[54]}},_T_211}; // @[offreg.scala 11:15:@82.4]
  assign _T_213 = $signed(62'sh64000000000000) + $signed(_GEN_10); // @[offreg.scala 11:15:@82.4]
  assign _T_214 = $signed(62'sh64000000000000) + $signed(_GEN_10); // @[offreg.scala 11:15:@83.4]
  assign _T_215 = $signed(_T_214); // @[offreg.scala 11:15:@84.4]
  assign _GEN_11 = {{7{_T_212[54]}},_T_212}; // @[offreg.scala 12:15:@85.4]
  assign _T_216 = $signed(62'sha4000000000000) + $signed(_GEN_11); // @[offreg.scala 12:15:@85.4]
  assign _T_217 = $signed(62'sha4000000000000) + $signed(_GEN_11); // @[offreg.scala 12:15:@86.4]
  assign _T_218 = $signed(_T_217); // @[offreg.scala 12:15:@87.4]
  assign _T_220 = $signed(-32'sh40000000) * $signed(-32'sh200000); // @[offreg.scala 18:15:@89.4]
  assign _GEN_13 = {{7{_T_220[54]}},_T_220}; // @[offreg.scala 12:15:@93.4]
  assign _T_224 = $signed(62'sha4000000000000) + $signed(_GEN_13); // @[offreg.scala 12:15:@93.4]
  assign _T_225 = $signed(62'sha4000000000000) + $signed(_GEN_13); // @[offreg.scala 12:15:@94.4]
  assign _T_226 = $signed(_T_225); // @[offreg.scala 12:15:@95.4]
  assign _T_316 = $signed(_T_215) < $signed(62'sh40000000000000); // @[offreg.scala 22:12:@215.4]
  assign _T_317 = $signed(_T_218) < $signed(62'sh18000000000000); // @[offreg.scala 22:26:@216.4]
  assign io_offset_x_0 = _T_175[61:30]; // @[offreg.scala 70:17:@168.4]
  assign io_offset_x_1 = _T_183[61:30]; // @[offreg.scala 70:17:@169.4]
  assign io_offset_x_2 = _T_191[61:30]; // @[offreg.scala 70:17:@170.4]
  assign io_offset_y_6 = _T_226[61:30]; // @[offreg.scala 71:17:@190.4]
  assign io_vld_5 = _T_316 | _T_317; // @[offreg.scala 74:12:@253.4]
endmodule
module OffregWrapper( // @[:@265.2]
  input   clock, // @[:@266.4]
  input   reset // @[:@267.4]
);
  wire [31:0] PixelOffreg_io_offset_x_0; // @[offreg.scala 82:23:@270.4]
  wire [31:0] PixelOffreg_io_offset_x_1; // @[offreg.scala 82:23:@270.4]
  wire [31:0] PixelOffreg_io_offset_x_2; // @[offreg.scala 82:23:@270.4]
  wire [31:0] PixelOffreg_io_offset_y_6; // @[offreg.scala 82:23:@270.4]
  wire  PixelOffreg_io_vld_5; // @[offreg.scala 82:23:@270.4]
  wire [31:0] myUInt; // @[offreg.scala 34:40:@277.4]
  wire  _T_7; // @[offreg.scala 35:11:@279.4]
  wire [31:0] myUInt1; // @[offreg.scala 37:41:@283.4]
  wire [31:0] myUInt2; // @[offreg.scala 40:41:@289.4]
  wire [31:0] myUInt3; // @[offreg.scala 43:41:@295.4]
  PixelOffreg PixelOffreg ( // @[offreg.scala 82:23:@270.4]
    .io_offset_x_0(PixelOffreg_io_offset_x_0),
    .io_offset_x_1(PixelOffreg_io_offset_x_1),
    .io_offset_x_2(PixelOffreg_io_offset_x_2),
    .io_offset_y_6(PixelOffreg_io_offset_y_6),
    .io_vld_5(PixelOffreg_io_vld_5)
  );
  assign myUInt = $signed(PixelOffreg_io_offset_x_0); // @[offreg.scala 34:40:@277.4]
  assign _T_7 = reset == 1'h0; // @[offreg.scala 35:11:@279.4]
  assign myUInt1 = $signed(PixelOffreg_io_offset_x_1); // @[offreg.scala 37:41:@283.4]
  assign myUInt2 = $signed(PixelOffreg_io_offset_x_2); // @[offreg.scala 40:41:@289.4]
  assign myUInt3 = $signed(PixelOffreg_io_offset_y_6); // @[offreg.scala 43:41:@295.4]
  always @(posedge clock) begin
    `ifndef SYNTHESIS
    `ifdef PRINTF_COND
      if (`PRINTF_COND) begin
    `endif
        if (_T_7) begin
          $fwrite(32'h80000002,"Hello myUInt = %d",myUInt); // @[offreg.scala 35:11:@281.6]
        end
    `ifdef PRINTF_COND
      end
    `endif
    `endif // SYNTHESIS
    `ifndef SYNTHESIS
    `ifdef PRINTF_COND
      if (`PRINTF_COND) begin
    `endif
        if (_T_7) begin
          $fwrite(32'h80000002,"Hello myUInt = %d",myUInt1); // @[offreg.scala 38:11:@287.6]
        end
    `ifdef PRINTF_COND
      end
    `endif
    `endif // SYNTHESIS
    `ifndef SYNTHESIS
    `ifdef PRINTF_COND
      if (`PRINTF_COND) begin
    `endif
        if (_T_7) begin
          $fwrite(32'h80000002,"Hello myUInt = %d",myUInt2); // @[offreg.scala 41:11:@293.6]
        end
    `ifdef PRINTF_COND
      end
    `endif
    `endif // SYNTHESIS
    `ifndef SYNTHESIS
    `ifdef PRINTF_COND
      if (`PRINTF_COND) begin
    `endif
        if (_T_7) begin
          $fwrite(32'h80000002,"Hello myUInt = %d",myUInt3); // @[offreg.scala 44:11:@299.6]
        end
    `ifdef PRINTF_COND
      end
    `endif
    `endif // SYNTHESIS
    `ifndef SYNTHESIS
    `ifdef PRINTF_COND
      if (`PRINTF_COND) begin
    `endif
        if (_T_7) begin
          $fwrite(32'h80000002,"Hello myUInt = %d",PixelOffreg_io_vld_5); // @[offreg.scala 46:11:@304.6]
        end
    `ifdef PRINTF_COND
      end
    `endif
    `endif // SYNTHESIS
  end
endmodule
