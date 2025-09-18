package cloud.karpov.checks

import org.tensorflow.SavedModelBundle

class TensorflowAbuse: AbuseCheck {
    
  override fun check(input: String): List<String> = listOf("test1", "test2")

  fun initTensor() {

  }


}
