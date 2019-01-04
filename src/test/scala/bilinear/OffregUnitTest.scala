// See README.md for license details.

package bilinear

import java.io.File

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class OffregUnitTester(c: Offreg) extends PeekPokeTester(c) {
  def computePixelOffregSum(xstart: Int, ystart: Int, xdel: Int, ydel: Int): (Int, Int) = {
    val xst = xstart.asFixedPoint(20.BP)
    val yst = ystart.asFixedPoint(20.BP)
    val dx = xdel.asFixedPoint(30.BP)
    val dy = ydel.asFixedPoint(30.BP)
    var xoffsum = 0
    var yoffsum = 0
    for(i <- 1 to 16) {
        xoffsum += xst + dx*i
        yoffsum += yst + yx*i
    }
    
  }

  private val bilinear = c

  for(i <- 1 to 40 by 3) {
    for (j <- 1 to 40 by 7) {
      poke(bilinear.io.value1, i)
      poke(bilinear.io.value2, j)
      poke(bilinear.io.loadingValues, 1)
      step(1)
      poke(bilinear.io.loadingValues, 0)

      val (expected_bilinear, steps) = computeGcd(i, j)

      step(steps - 1) // -1 is because we step(1) already to toggle the enable
      expect(bilinear.io.outputOffreg, expected_bilinear)
      expect(bilinear.io.outputValid, 1)
    }
  }
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly bilinear.OffregTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly bilinear.OffregTester'
  * }}}
  */
  
  /*
class OffregTester extends ChiselFlatSpec {
  // Disable this until we fix isCommandAvailable to swallow stderr along with stdout
  private val backendNames = if(false && firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "Offreg" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new Offreg, backendName) {
        c => new OffregUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new Offreg) {
      c => new OffregUnitTester(c)
    } should be (true)
  }

  "using --backend-name verilator" should "be an alternative way to run using verilator" in {
    if(backendNames.contains("verilator")) {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new Offreg) {
        c => new OffregUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new Offreg) {
      c => new OffregUnitTester(c)
    } should be(true)
  }


  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new Offreg
    ) {

      c => new OffregUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/make_a_vcd.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new Offreg
    ) {

      c => new OffregUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}
*/
