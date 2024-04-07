package lib;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ApiCoreRequests extends BaseCaseTest{

    @Step("Creation user with incorrect email")
    @Test
    public void testCreateUserWithIncorrectEmail () {

        String email = "vinkotovexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    static Stream<Arguments> userData() {

        return Stream.of(
                Arguments.of("","25369", "Dmitriy_D","Dmitriy", "Ivanov"),
                Arguments.of("ivanov_123@yandex.ru", "", "Ivan_I", "Ivanov", "Ivan"),
                Arguments.of("petrov_234@mail.ru", "8521", "", "Petrov", "Petr"),
                Arguments.of("semenov_147@mail.ru", "74125", "Semen_F", "", "Urievich"),
                Arguments.of("gonchravov_41@example.ru", "749658", "Gonhar_9658", "Concharov", "")
        );
    }

    @Step("Creation users with incorrect date - email or password or name")
    @ParameterizedTest
    @MethodSource("userData")
    public void testNegativeCreateUser(String userEmail, String userPassword, String userName, String firstName,  String lastName) {

            Map<String, String> userData = new HashMap<>();
            userData.put("email", userEmail);
            userData.put("password", userPassword);
            userData.put("username", userName);
            userData.put("firstName", firstName);
            userData.put("lastName", lastName);

            Response responseCreateAuth = RestAssured
                    .given()
                    .body(userData)
                    .post("https://playground.learnqa.ru/api/user/")
                    .andReturn();

            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertJsonHasNotField(responseCreateAuth, responseCreateAuth.asString());
        }

    @Step("Creation user with short name")
    @Test
    public void testCreateUserWithShortName() {

        String firstName = "d";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", firstName);
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, responseCreateAuth.asString());
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }

    @Step("Creation user with long name")
    @Test
    public void testCreateUserWithLongName() {

        String firstName = "pGphTvWIzAWRoNXZHvsppERSwGSxMDUTUinnFoawHdkEiOSxqotmXSqGexbYMbIOhTYrrBLvdazXfFvxtGKMLLxiUKskNwrZETMkYeszXJPZMFwKqPSBKQBQuMtbsBvhUiyrQyVmVWGyXoeotBTlWceJdXwlAzuKfDnRFabNRnzuTcVHzuMkxathcbIDHHhVCrkTpIfoyzUhgbYieEsfdJLiHNiTZGENVLjbDBYZSbEXsZuXdEIeQWMqRVP\n" +
                "cOAPujUHUVhMHrYHzrVLDuquxulkbCxLOGvhQPrjtyUQgdonmLswwfnrsyIHBwexKwOzsasoAGZwuPmDaOHANkHTEZJpgbzYHmuUnseDZtmrIrhGqUJjTNEGBIcBjVRMJSKSqklvrvhvTHLnkNQtvQshAxyPqeALfcyVgVzMighMHkdOuxSYtRiaDcKZoFraPpGzQKOecENNqrZAUNCQTzlrPVhQBfvkujBbKakdJommHYdvodEmtVkDstE\n" +
                "uADjhTOCloDQBMueuOquqBHmVDaViCjeXwQWbwHGnGesPMftUUPtWNdFgvzKEBStQTnQTMbrRTduwoUDECOotPKbOrqUMCkhNQcnzhcOzswPZjIXaTNPwQcgwIiUfSfCIjblSLnaSocUkWBEfenaCPUMWXaHpDPwQHWwJIPXEWoGTGRsbYrSyIbXXsRUldPgSuOGgeKjkkvyCPDjrImfjkBFYGdvzMEGOzkVmLAyjOvLAtuiDuTHMlnLLQT\n" +
                "WNGYqJwwoILNztMAawMqLMFwRXQMXevjtnPkjTAMvzrBCQzahSJRNShCknszjvZctzOyzWlMCeyJzlMsvGFiSQufTSOEBCXLnmyZuJhKsLYAkVaYYpglnHMWEyQwzOAbAmRhdUbRivbCtxemPhzGeHnDHhOmNbUdjXppjTSmizicpXnwLnDzqSmElPdZPySfKjANNxAbtBeQnItQddlGKKyDfSRLCkFGFqMRUCaoSCTCGJxjnugdYuGSEjH\n" +
                "ufNDrlNksGNPkEGUQheszWWlhGQvXEKHfdyFAbbVAOKAzOIApjrsBNsxSanCpKUENpjbDwmHvduPeOTwoFzASeTlXjJgeBFAOlSpGxEuPmWTHcUvsOZvHDkQIYZqGvCEleAXEZcJczaWRgmfIEGGjAuOQxAHpWOBsUwzGgyZYNaxUQWyGKdycbtSdaEDjyMkbFXFxtXXACkmFtgrxigKZuQfNUqLYDJjzwBIybOpoOoOsGegovVcorQisqB\n" +
                "BMHRRaxrvXAkvlPYxTANepvIafbSmpAqTnCUHrjtrSziKfQOIqtWvkQmfbYUYgVMLnLmFHhjMzhpEmgAdmwGKpOsYiOfoGjpCCwqXPtQJNQsJJoIAHSZpYrYcABplwflUCFWRvRdtdgjmDdXaxnVNDxOwYMtIXFJliVFlvrMXQSyzCDyczuPxmjYUhcSbNBUrtIFjQNubKgaunRrrKFhFqRYXIUUFKdumgkLXdTuJSChUpWmMiwXYWzJzEQ\n" +
                "DJFyMabEakPfFdAUeZpyeHUsoSacBZipGQcxFYAytriwtZbJTKYlSHNGRKmyWwQaYinePUxBNPSMrhNqlkxeOBrAQyTNGmfmLPlhAknFPvwMyfICgTiFSyzmaLqFxemrPONzSpKGksLoNdXgRhBFoqSZnGOryyMIwEyVpPnIaZSplGjAlREEqCZwWYnqshJnwcMSdAQbXuDhhjMZNuDDfbakPCCkfNjPMQQBUMwsWZayFygCzeUmFdzRgaA\n" +
                "fWcmAzdeVoKRzRBQpLoNWgiTdYzBGYBtiMomGfXqFFYjkCzvLjFIqMAcVnWWAVZSZERbkCFKHbLzmdHBpoNQSDQlQwCuOzKYztFOnmgwtEOBNsulLgCrPcoKCkXyqNUfvjMfuFDzibNrerQmpyMfBmJNbWoLhNyOVISzFEBjpnsEpZSrubaSIkSbUNXQKnNkmnsBdqceuCyxMeVfhvWIxgKuWMRFkNpuZlyuSxMuOkIZtniUyBOnNTmtruG\n" +
                "XqEBCybsCXBIlAbZzDxNfiApilFVnJdRtQkTsSMxjDJwKPGhuJJogToJgaICionDxEWbHeMeaeFlUdcVItZeVTrPEtoFMsdtLZQTvxoHHvxcsuHoqGDihTwtxWcXxOUVNviHxAfkUKaQLdOSSgajEkqbCBjSotcgZfxclwgMEiDYVSrZMNoKFvrDNGOuwXTgvPTycXdqOmgFcrTjFLjTkhRGPNkmNbIaEfXBjdunktgEQRGnyxtAhDFVoOd\n" +
                "vrgjvcvGMBaNlOxtvJzrEkRUDjOiqRFBjOPoFuCUbpRfPhgAGPnskdtSRlUPAoHpCAXAFDLrQXNrTmjuXXUAQFkgSwKPdVGOGqzznVCYdAMbqLCodFCalfkfGZpAxthzVVLtgvPWDILeZAdkMwOsfYxRawJEGOdDyrJzyRJfXjYZaLyBafhUvlXozBbVdamAdIRMdxAxmrsFzcYCzrVaKjZvIckMLKagLOpXbgQyBsMhjIvydBqJdlMsJuT\n" +
                "FRCXOUDvpACeefjkwjDGyprQfDUrECccuZSMxiPrbQVMYIvcgDrqAbddOfSnrDzwHibNGTxdoyXvObohSHvSbcwVQfHssJmCjjRxlWBfxcTCFhBBWBeKStXMNEJgROpPMCgUFYPQwLVEqkAvgQTMhuVMislPwRbQySXQulinpQOgohvWrvuyQqXQuwGzVUeiFauTawZFqpNIhcvSWsPXyOZYcbfRLPRurwQVsvpSHnWkMCKgZJxEZmdgHil\n" +
                "hAwXJTAcRCQaIffJhKVJUkpbLjDQPgDreVGWGDCbqhSZMYtEzxTDivKivpsgnFZolyFTuheWaOWjGeEcZoKFGkcSDhiEkAvQyytSUYwzDBrDTEiLiMAzxhAURvbtVjjMlfIGyxqRlWwmwDVpryRFWJixedgrogaaCjBjqaijrdFVuWLjXMHBmnsUDsyzWSEaGCJFyzWZDPGJsmcfLfjvCXNUwCCqWtOsHmUbEiLlbzzjgfogNqtgAuhPVXi\n" +
                "xreycxCGDCLraJbYHMlBHOZIRplhWEqLcXZBVPAErabZaXQQMqFldTipkrODrfTaCeZgAVFXmSGSReSDMshdFigowRhiHGAGFlSFSLmiyuLhasLiVFKMohllHECHFcRfpcKLLvTRWatZjckyeConJmtTTSeepcCwEIfPYlmTqMoMpRHycySiDjyNvkGqothZvYOHpIKQieTvHpClbkFDcywXWVmKDrOcQAYaGHPTvUSPjBycxKZxqCioonv\n" +
                "yUhESyuRgJaziJAnudHAyJgLuPVVbFDBOCrAIMfzMlHZfZTDeGNOhbsKXeZRZaFEFWTUkxOCNXdoufQLwYNYMbVZlvHzlmVGrhmYDsPmVXwVcLQkhcebGMehDGNKHQLPjSENfoyJunoniEmNlQMFbEoZKtGTObPBTvsxxtpUZshlICNKwiqQIoMZKLJuoQqLOhPVijqLcEhUhljXAsdxCRweMwVErQqsXAVUcAzNvnckqqCkNLhIjgZzBvl\n" +
                "wUSOpIIyLFkkGgsIkrqaPnbVsETqerWgcdXscehMVwIjPaWZZomVzkwGZyrEVNBUABDGoprnOivBrxMxeSIWABNdNlNzxOafSPLQsbTEAGKfLRVxrxRFtwvZQVLOhNDpmaidtdsqxqrDTyaXLGNkqZKjDJvdawOOTxDmENGDeaQaVNdOwOfSkWtaOyhmzSfvAqTucifblsjpdPosbFchgTVcqfGKbXADfLyyucWPeXiRhEqQBydKdrUjUUl\n" +
                "HghXwdCLUCDykWoBMNSseWkiaHRDRYuxVxpWZuOxGlUxWHvjFvnpzmkNWccNDzOmnwGPeXTBPeCnZVItMwmMLdGrxcNnOOAWLejpVgtMOjfpLZvCaPwrsYGfJfCxtgXdzchGHwGGqnEeDODdekGmGfCvMYQiRaKDoEnnIisOgCgdwNlCkshLFMzMloRkhpsptUbmsUZXYFCMiIvOckhHYgCwirucZGoQeRLaMwKkbpdOZUpwgbYIHmwDCxc\n" +
                "GQIaGFHXhstjgPnMjkqRHfjglgBBCpmgziHCJVRFLbaVFpsjIzqfTzhIqdFkqPrlAPlVHWNENxDASbWskcCohDMuUwrHDwjdGSUimqdfhQXqjalwOGQFBjPeuTaSNtsxpfFGFPQxyLzXMZyiSXPNlCyfKIgeTXjwHhAVyJKPrMejLOPSabIEypwNMqWorAbycrDeFyhdeVwIcgumBLEPjwEfusEwoxevITvFNpPyQemuGEklDWbfGZRaDgP\n" +
                "IqCCLJpLHzGSPvfaZeSkdfwnhFQJxKQEVSMvMUlGbGPyaxJAZtLXgQaEREQoMPNGNRlAlXpzmWBhDpWoIOnLJUbBiVAaksVTKmZcdRTOxykVICdsayXZtTFnEeMKEbaYpqhCXRYRmDzEerLvedZbpSVDgalkekeLhmVVFcwfDzIgJatiFvEQbUkusfkOsndZtUTcxgAffQsxDlySftEgVxEqTJDlOfVibSLwanJONzzIXVhAUIcTFHIATCc\n" +
                "EEfxmCehRYblSgaZZPiaCxZxylGrGcWhkNvJrGMxqAuMVqTNIhjWCuHeaktBvEyoxYTWMoGMFUKEvEvOgshqxfnnQOCeYJvmFlIUgNQQAxIozoNsSRWEGBSVPQmJRebmkzZpuvhpVUQvIwcTtxdcMlCVVZrsMFlgPoEKuzoQOXjzBMcQaNUmSTRNsRQUWuAHGziXPIxQkdGfDUydWtEDlQygZlkDVUHvZgBWsTpaWdbpHkIexnvtGEJgJjk\n" +
                "gEFDUKYRaOWfQnNIGuCQkyQlwjRAOyMqZCKpaRcXmrrEgVGoMzhTOlDUvlfxhibDIUmceAGulPKzLkknRSNnsjiNmvMtZhoZcTptlpBaxnfNxhqOXStGzvxVItRvFOfXOvlcUihXsrgDSObQLnPJJkwPiVEiFYzPfFKrhyabIDAhyeIbKBreDXvHGtjGbxkENIJrwjpFrgxmQsQXrmIPGEUujbgfnawxhpOJhVbFrYexgLzOILzbqNcuhRM\n" +
                "hwOxDYNTgKfeGLWqTHypolhljtIoSawFyvDMeeIiPonLIOdRvkYCdrdlUItwDglcODIikUuFTJCjQjEEEwXirkauqECmCBoCAzWLihcQsSdPfzRrFMWmXyuvjBmJhNpqVpeHiNPmPXvlnVGApQVvBAheBtQnybNTJxlZyToCczRtieDKaSNiLrtBHRhTzVUTHYPORUFyjQgLwTRElimMKjmbSqdRPsNPvCuweiMbHtnMjBQSAyLvSdpYujm\n" +
                "JDmMfGYbQrYdBakKEUfZkEziQtHUAQFYuQLyELBawFsMKwEgVMqadEdKEmfBNkfzOjlZeHPIcVBTCrfCACapgzpPzkRZMyiHceXFiKmLvYnkkwCFgcAbKOtOXNrjbzMWMYerRSDmCTcubUEmqEsUxrntBSAMuhXSgmNjpjKwsGlxnxwFgVZvmVbXaRBdMvPVfZcSbTNyFQzPrNULdcmrUtndxSOCGOQLAemqcpPHDNuzLFgjNKVMNxrsSOZ\n" +
                "PbaeCdoMikjUThpABjrOqFyvlRWKHbRAZDyCwUlAcZLhnwiwjrxpfpkVkVHqMuAnQySRngfZXNjuezxwmdcJJXFoJutbfmwhIprjeEPkIYnjSCeLUkeyZjAglerbhqYXOJXIdDKuaGrRRePihWPveMBUcekwCRxjiBjckCBqIdEAnHyYjqBQkYLomcqItGVtszYzmtsMKOilXUEYMiEXBHviXaUSvNpfKHQCyuSbfiaNIhKewIHiIMShfdk\n" +
                "LKmIHaqLTDzMISLLlYQMvfJSqbPROedhViRRGuyfxanxAiiODuGKubQJbIILgVrxVxAseyZJYoTqShoyeMOWxMDFxVfaSVxVnzTTGzIocUwDeHzFhMYtYxefTutBafFmkPUPXjQOjNOcxSPKBbnSavvlcLYKdmCUFhkNetleDCcLVuazYjQcTFnAloiNeiwiJxpJaXYeWBFNhopxbunocDTorDDFjFnkdNiudwJbuzLCqEVZUGujbPMLUJO\n" +
                "fbTgOhvkThVMEUYwvfltMRMluAUhzJKsmZXXilpGSaEXvuETnHKwyopRfUCRdzddUmQyQHyvRrAozUwweuvEicsNigvgdpSfWtaASgpptBnpJftgjcqpjCuoSBJseJIhXRHzCAJzyWdqKveUYxLTGPqUOdbYybtWEMlSUwfCfCfCpIVUKaCEIgPzhDRtTadFFVPdtykGYHUhORggalHGqoAQeQffqsdeuASXPcvebnUGZTlEVqrFgYYiBbQ\n" +
                "RztXiFJMQjzXNwDciDgyAaerVZFJAJckSuOmQAUhdkmigFDdIonReEforENrWPDLoSWvkbtmbeHNSbWGbTKQgjYmceJHCtErQMVqQAMaQKhQtYVGgOWWMGRbGlwtOtOHuvpLdWmUEuGMmKVfDkiMlhsAGaWbXyxfCYsaRUnSnkPtKHsGMhYLXSXuonLPmSzEmpYvBAfVIVLESQWRncGYFklGguTJvnnJnNtlLxENForvkeYPgBmIdDHhaXR\n" +
                "iNEfVQnhYIZqeUdvnhbcvSvcGfoERWbRIscsTwjAbvbziGBLoGQnRRsRBGPZSClvkmIWGrCuMoJlCFyhiUtXQLrSqoAedrNnpfBgyrkAkpeZYWejfrcvIdWDSnZQQbFuMrqVzDLZsjiYYsTodlUqgJERQlLiKulVTmIsHwhMQDhIoVTFSczkDKpOcGqIPDUhaNNBmGMVUKGqMawXpghRYeRQXlUGJoohVDfmjSlhpRitcZiGyuYtKUOkxjC\n" +
                "VLvUOdxxAmidHoPPaAFCKuLCWQGFIKOsTYeHepPKhzxljkTHLHpHARrhcMNbUlnvLsLkjrFACPStHZkwqlFYaHEMBIRASQzTQhmRnxOQQzCApNHiCZvpKrYeYXpbxfBFKnOqPGUcHmKlbnqRmFuTWSJMZMnzkteAdLzfuCVokrUwvLoYnuBwuYRWEqsmGlIHDJEBNEbxtsUqWOtFAmxuWifsxDHeYlRwUsrcozlWWvMfHzrdkzjJqDzLqXa\n" +
                "IihovEWdTRFTtZqFHWmoTlsgbXiWlZUvlNPBRajhFDOVFxIKPcWYTFDDreCJOQSLIdrNlAlwcaLrpvrRSkkggvnobHROvXFTnVoUXdLXQRMamkGMiAqMjsVCxGZQKzXxkXFyrrAlwOJhXsuaKVgkvWnPXsXsTLVRogdjwYmPMfQnlGwQnzqNhlhDZTBSVTZRnyMVAGXpGykjyeGqyVchxkquPZztABwVDDtDqLOCKzsIdCRsvnnoCPjhFiW\n" +
                "FvAXBNqLFxJDVMTfIvEbjgwXHaProWTlLdLRBFMqsaLFGCGrgefoENMCtmtdUGazjljDHXbzdEDdLjpmMojrNAKeQliNHnuUsvquUxSLDxabWpfcYAOYMDdQbasGReNxRWwcXGUHCxtjHSGrfnhhVdprRQvMVDrjnSdQZXntKOGqLIunhuXlDiEwjQgvifwIIptyiGINfsTEpiSVdPlxRNAiZbzADBWmtKetDawGdYUuunIdWwsJYfuIxQo\n" +
                "WseQtZOTaFomEOeWWjgUhlBGfaLpcTYlaxTAswqhZXUFxZGZQctuyVonUgurqnxqpZgxKInHVuIkXFZUmGcArWoTwxHKZUdfhKtPYHSOzyBnTzodtyUdhEebdpwxpCktNxhrwzzDzvYJergjCCXZFZnPXnjrIEBApOgRAfWypBUjXNDTSjPtHfhZaUNBkwuQawsWACYkkEQUeJeyddHxrUVtdxFVQLNRhGoPUiLBQssSlHrlzFYwYZIUkgs\n" +
                "LXboMCYKxjNkCvEhJlXNyZaYoYnQvkKeazLauKmDUEOBqLzcqFrkSgDFXlcaRsNxtELToyVDrxHXyIrOlPZfpTOdCgTHGsHODmzMiYQlymktgSKLdEmQASNdolotYyKseJQyrzpVyAuZIJQJdnrqwYVCxYUHWYFsqzznbbvsSkMfYmUYyqqEItvukWRJGhlmlCAYrsQQDHeXPkxDnLbtcsCEoKFRtQdWwwxizoftGDqTwLrwhWLyedqEocp\n" +
                "avvrSFGcMviDbMrELdbWppQzUOUAfqkbWchLhkXQSyTsKqSyjRajpaVPXzGFEJaHPdhXgWIJZCHzhCIHZZiXnqKNMlbuUxBqNKxNtdjKkWNmojGeAdwHIHCRgIPFSMjnahpXVKlcPBCJZmWljreYXEJymmoDWKdUPsMyBqZpgaHeVTJcBMowwyLagXFEdISOFWDiptbKbzhsjLsntKeBTxzGCEPMzrzaQGvldiJDoVHMiQOxWkmIbXrtRLP\n" +
                "rsAFAmUVzlzrAVSuJgukyLRpCTvJzQQvrFpYLctWskkZvEivjUDOfrBrRacBVHsjFcsYlTxCfrjjEBTcsXtvSvQgmOsShLZfsKQbUoRjVBkhJtejgRJAJoHrECnLWCXOrmdRVJHHrAQtjIulqMkJealOzHxeTSMkedWdzTMIvPZAUyJrnyDqGVZiWvTguaodCwpQCeJtmLdlkQJdvmvYbLjIfsXrOCDQUirGqEvFxVUrLksoehbPFqKOCkx\n" +
                "JGphwBvpHIksCkABaxHusZMFvcmzWlTqNJXdYOxePaMfCSnXOjhGEswlfnbrsISoUGucfMJhLmyCzjmgzTWDEcRHQWBqryZohxoKPsPlmqArgizfFmksACjfTOWgXumtzmNJYXgeefcrIBJupCRbeSDJeFFMrimkROVMUSMcQlZLModlbbQsdaoYkPOdLtFhipeRtsdPjRHvlVkkxqPOiWuywuiiqDWTDhjyZeTPyXbduIvZRKueuKJSxGj\n" +
                "hIrBBSwoRmDsvYBzwngoiVSnrjQQpzXnsDTbVAblwgBbFCGtjRWYTEvHcPSbEGVOikYPpXFmQpjkHKOQwcSVEXRDnCgatDmXQTYRRqMsKoHFgXuUhqUAcKTrNQfGMwmMykjeWLoTuQkgEjbHPJcLrIJFTEBzfiCkAtCyomigJEJAqiuzdQfvTLASoWYMXvxvSsBfSIDwMQIZvePwAUESHvLnioSHxeMxDxSNQERXKNwvyyjYsuMRgKKFtpl\n" +
                "hAdjVshcwYPcWXhBwjuFYgFHUBMeGJdVhfqRHztHUKcYLzCsXmkedueLBophuAGJIbwYwGkGCsQCjMmoseCIacwJmqbBXOWSUyWikpiqQGjlquBTdlqMILlSDpmtGKAvxdBEmOzGBooYgKqRFFUDEnDsYRDEReuKlRtQwohQNHXILjKPmCpvbETEJWPFtPGMTGFOcAStyplZIgHkUibZppxrImsUMroSPJhsGvfESCuMwKiJaytOzCexDAk\n" +
                "iMaobxGtMBfCXgmWlHIePwogtPvxRbrTpjLrOPMRgkWYIZDPPeMaXrhmlopLOxQhMarCiiBcSGddAKhTdDZxNVfbVpGoQubBIiKksKUtuysnhcYfsWgwRfZrhfekfwlOUHPdFTGEDEvmEmszjzdLilafJLILmktAUZVVVxyXIpuQBAEVUcdGBjDfgjJCToKlNPJTVMICFzESfimydUqqNoRGugpjjmhEDaruTaEnlbVpBNIqOyxSsUaANxL\n" +
                "zpdzXyZNVmoDLVxTvezcyvSJTEuETBbpjOsPzogIQVuSBkQlnCczogQtGbLAIaHGBvUbJsjYAipGJHfwXhREdGuqOqXCdHTUKfRqYYdMyEwyaVGjulQEKrgTYIhpQLCZNTQeZkikPFBQITKxlQagHnxbbdmWlERXKuQAnklbHubKyvgFZiJuVDSzkwcvHAvEuBDXAnMNFuHriQVvzExtOgVcMDZMlFDWpSpgETgYmziGmdiHRnshgGcjMEl\n" +
                "nnAbZbupFUNYtqpUHJeCPOYnSIakYDmKqFDjPrIBydxioWImnavDxQXABdlWkQnXgPPIrGJjrCDSiPLruQUUCLVpWJSrUmpAVwTZaRESoaOGiQdakPffnBsknBvWypHJsrCnGHBOCyMKfdOhvRFhixHRKOXZazDxtaZtFFikdmHffyXTwsLTqTRVkvIoRiRJLgXzCwsJHztzymVjyySyoANeQnKRvyLyjuWoVRMIPoMCxMgUVBJBYPZrTOQ\n" +
                "EfgTzNxOKNDlIhiOiVcScAIdpQJrkOaFNdAHBvVDWsxFDVuswNnersGPeSixLIKdKUYVDOlkQzrwjGmBzZGAvjKDSIPXThfxOjtcmKLugUSrIauzqangpSLvKMRYZpQkqSCBHOJDpFgHOYcJFnORJUokBIcknQLLMZIUEyCVFyVJcFWCiskHfABvNDkyKkQUWAJCrcFsBecyUeWzKvbNhoxDTuDBTyXfluayRQLEXSkdfmrllbMWTWxADgH\n" +
                "RMQSjIhfNeAjUKSmoCBXNXEaetbWqVqncjFDTjVEzlwDaVrDDuxpcNlLVNyHjWXMbdsxOElZQFKjDETKBzhfYvdNZuTmWZgVtdbFulezZfhvBHonNbsvSNoscHpzNJSrIolENsQnjUbEvpthEBBRkyeVRsxVaZBBCnKxmRWZFRzorzldHEKMmrHpzvHihMZRsJvTmURYlBlLvqOcoDhvegwRBFUOgnicfKozIdCFMKCSiafZkDfGVAPbFXh\n" +
                "SXigpxuRJGHSTPBMNfRbgESqquvPQfdxWcMlnpRMmvcwQIwlLGTEnPZzLGfSNQqhvtAqnRAukQcgkubPfKaVvAwVCTbvulraLTCDfjbYFpuDTjcCTZSnhIVhONaYiyuOBAQenjCYwQLjKQyZZjwLBOoxFIthmVsLBTOZBQkxbGIDiWXKNmPiiwBjbNvRJrMuZWPIAtLdoopEIwlZJLpHxgYXsDRvpuHaKBVSQKBqRjMAAocjmWqLjbLVUAZ\n" +
                "VfaJqfsDCDoGaPuZdzwGMzIARBfwfrOmIiOeXnUIciWJGFBXhlsYKzxpaHmwcBGeNWtKqBMXpShBDSBRSsvuDopoVJVCuypFkPGdeYlcnHIOYBuuizgIKXtseBvizPUwYebkvtndcrIFetHtkVSHxPGbUFpDAFQeEMNYthLnltRccIuGdghKPNqLpLCzcvshQkonlAyIYMuxLCIswiLUsAwgzwOoUKvjtyRmTXOYcfysVNpqRLGUGSVNqxR\n" +
                "CckuWxvbiUzoaxzXokRFBOqWQhoLwTdfqgWARqQPhODZmSqmpDEvEDegoMEgiPQINqMCUKINKyaMiKNoouXDdMIrDOsNWOTbiJhCNGNHtsdtJSLxjJvgYDWdxoQdZuqSHuuvGYbqAislxCYkvWxdnObRDvfFnhnzoXAOcLtePsbxDOerSPjObEnbnpVzielSjpSqsFQecdYgpWjfQMIgShckerDcunknCSTnYunzRuwuLieClvnlQOymmHP\n" +
                "ehcUqiZmpDSReorDAetcSursRNcTSYofZeqcjSglSRHGOMoFKcMSVCufugIAxRDKFwJNYVerntGczOEhGJGRaqJYklmShukVAmHNVVDEwCVzjWPgHZdqkVSYVAeGrRJAkglXcrbGFboOSqSJCvHBYnrIPrtKVPIuTGGVmMlcpXcCVyKQCIMqJtolHikWGMEgcOsdNjmxvseUcinqOpjtVkmwtAlHSxxwdWcIBADNhadHubuanNypgvLGnTq\n" +
                "rnQAlMHdaxGEGFMvHscPrviuUcfNhVaNotrxnmCMmLUkntOcEwScoJlTaKgkensKretPfvMkqKeCoyDNTvbCjWuWlHvxTLoHRbgQKHTJiGYeZIMbZRxHzVYKOzntuJPEBKxOqqEBhiGVKmznaeWHvsKWiyxZSFophKeSLruZuAsdPvqWQYyTuPlCMnabFTWVFNEFvXHWQuUsoLTueFfdWyVaYksdCUbNUCzgblPSpsJmicmlqOcKOfDvOwh\n" +
                "YjkZONacYSzzCfXYhvPKcRUizQfyTJQNgcglmjlqLsOrFeRkcsxoBLPnaJKjhuiwQRhmMMgDwLxosigqduzYQAdghRSeSmBBhIeejgdQgTrUHrIcrRknecHFcFOwQAghclFteuWRmnQypXTnmBFJfWbQDnNYjqMUpaLFOYOYqUgfCbGdxSdhjPydQFdwPTjNZggYMqkStlNecTVAITaTfucrvVGBUNqWGYOLSwtMtCIOthsqqtAPbnsqYAk\n" +
                "WAfLpCIJoFucTzcCtVgEweQApmWBVGgPYcHTcejOlXVDRFihaWAyKEHWCSxdHnffWyyxWaEupRowfkXwrrJlZNXddUYmwhbvtgDfTNzrUqfsyWlbBDFYgHXwcMLUHBRvqogUDqxYyNOOQgqpjGRzJxWOElLHhDCYBzZymgalYwGyeCMRwaugJSEnkBaCTPnYQaiVpNuLvVtfKsSubcJowMamNcsZwbZIWPUbsyZykWNgcsBGXshKOLiwrNS\n" +
                "TKSYnZEBUiEmNZBsYLGlfUBbOdbWUQVysWToYgSPGvEmkyQxkDWVJhrtnbUUBFoVLMvZmloGcwxNaqGOtbNDTnTQWrlXbMtIjPhfaSOgfdXfnekleclZZNQtPXdjTxIVzOKZXBqwEKaAqtZtDkwuXPreLROpaOKBvpjzCuETvgdsOrkKnmIhGzNaMDJXZpaaXIQGFsqVNpXgLWKMiEwQkDyJSajKmPMKtYloUHhvMRbwLolBnDipRvVzxog";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", firstName);
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth, responseCreateAuth.asString());
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }
}
