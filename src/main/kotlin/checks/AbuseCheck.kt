package cloud.karpov.checks

interface AbuseCheck {

  fun check(input: String): List<String>

}
